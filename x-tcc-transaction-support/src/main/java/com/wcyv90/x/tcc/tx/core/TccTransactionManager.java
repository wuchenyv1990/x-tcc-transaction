package com.wcyv90.x.tcc.tx.core;

import com.wcyv90.x.tcc.common.JsonMapper;
import com.wcyv90.x.tcc.tx.db.mapper.TccTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.wcyv90.x.tcc.common.ServletUtil.getRequest;
import static com.wcyv90.x.tcc.tx.core.TccEnvFilter.TCC_HEADER;

@Service
public class TccTransactionManager {

    private static final ThreadLocal<TccTransaction> TCC_HOLDER = new InheritableThreadLocal<>();

    private static final ThreadLocal<TccContext> TCC_CONTEXT_HOLDER = new InheritableThreadLocal<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(TccTransactionManager.class);

    @Autowired
    private TccTransactionMapper tccTransactionMapper;

    @Autowired
    private TccTransactionManager tccTransactionManager;

    public static TccTransaction currentTccTx() {
        return TCC_HOLDER.get();
    }

    public static void setContext(TccContext tccContext) {
        TCC_CONTEXT_HOLDER.set(tccContext);
    }

    public static void setContext(TccTransaction tccTransaction) {
        TCC_HOLDER.set(tccTransaction);
    }

    public static void clearContext() {
        TCC_HOLDER.remove();
        TCC_CONTEXT_HOLDER.remove();
    }

    /**
     * 调用不用加@Transactional
     *
     * @param event             事务动作名，补偿线程触发补偿
     * @param compensationInfo  补偿所需数据，比如入参的json
     * @param tryLocalAction    本地事务动作
     * @param tryRemoteAction   远程调用动作
     * @param confirmAction     本地&远程confirmAction
     * @param cancelAction      本地&远程cancelAction
     */
    public void withRootTcc(
            String event,
            String compensationInfo,
            Runnable tryLocalAction,
            Runnable tryRemoteAction,
            Runnable confirmAction,
            Runnable cancelAction
    ) {
        withRootTcc(event, compensationInfo,
                () -> {
                    tryLocalAction.run();
                    return null;
                },
                (empty) -> {
                    tryRemoteAction.run();
                    return null;
                },
                confirmAction,
                cancelAction);
    }

    /**
     * 调用不用加@Transactional
     *
     * @param event             事务动作名，补偿线程触发补偿
     * @param compensationInfo  补偿所需数据，比如入参的json
     * @param tryLocalAction    本地事务动作
     * @param tryRemoteAction   远程调用动作，有返回值
     * @param confirmAction     本地&远程confirmAction
     * @param cancelAction      本地&远程cancelAction
     * @param <T>               本地+远程 业务返回值
     * @return                  业务返回值
     */
    public <T> T withRootTcc(
            String event,
            String compensationInfo,
            Runnable tryLocalAction,
            Supplier<T> tryRemoteAction,
            Runnable confirmAction,
            Runnable cancelAction
    ) {
        return withRootTcc(event, compensationInfo,
                () -> {
                    tryLocalAction.run();
                    return null;
                },
                (empty) -> tryRemoteAction.get(),
                confirmAction,
                cancelAction);
    }

    /**
     * 调用不用@Transactional
     * confirm && cancel 接口需要一致性，此次为有tcc事务表记录则进行操作
     * <li>1.  事务表记录trying && 本地事务，原子性</li>
     * <li>2.1 远程调用，成功->[2.1]，失败->[3.1]</li>
     * <li>2.2 远程调用成功，变更状态为confirming（新事务执行），失败->[3.1]</li>
     * <li>2.3 变更状态为confirming成功，成功->2.4，失败->[3.1]</li>
     * <li>2.4 尝试远程和本地confirm && done删除事务记录，原子性，失败->[3.1]</li>
     * <li>3.1 失败状态：事务表：trying、confirming、canceling</li>
     * <li>3.1.1 注:confirming需要无限重试confirm达到一致性，其余状态需要cancel</li>
     * <li>3.2 失败状态：变更状态canceling，失败则此时保留trying状态</li>
     * <li>3.3 尝试远程和本地cancel && done删除事务记录，原子性，失败事务状态不变，由恢复线程执行3.1.1</li>
     * <li>3.4 cancel成功，满足一致性</li>
     *
     * @param event             事务动作名，补偿线程触发补偿
     * @param compensationInfo  补偿所需数据，比如入参的json
     * @param tryLocalAction    本地事务动作，返回U
     * @param tryRemoteAction   远程调用动作，U入参，返回值T
     * @param confirmAction     本地&远程confirmAction
     * @param cancelAction      本地&远程cancelAction
     * @param <T>               本地+远程 业务返回值
     * @return                  业务返回值
     */
    public <T, U> T withRootTcc(
            String event,
            String compensationInfo,
            Supplier<U> tryLocalAction,
            Function<U, T> tryRemoteAction,
            Runnable confirmAction,
            Runnable cancelAction
    ) {
        LOGGER.debug("TccMgr begin trying root local.");
        U localResult = tccTransactionManager.trying(event, compensationInfo, tryLocalAction);
        try {
            LOGGER.debug("TccMgr begin trying remote.");
            T result = tryRemoteAction.apply(localResult);
            tccTransactionManager.confirm(confirmAction);
            return result;
        } catch (Exception e) {
            LOGGER.error("TccMgr catch remote exception.");
            tccTransactionManager.cancel(cancelAction);
            throw e;
        }
    }

    /**
     * 分支事务try
     *
     * @param event             事务动作名，补偿线程触发补偿
     * @param compensationInfo  补偿所需数据，比如入参的json
     * @param action            补偿调用动作
     */
    public void branchTry(String event, String compensationInfo, Runnable action) {
        LOGGER.debug("TccMgr begin try branch.");
        trying(event, compensationInfo, () -> {
            action.run();
            return null;
        });
    }

    public <T> T branchTry(String event, String compensationInfo, Supplier<T> action) {
        LOGGER.debug("TccMgr begin try branch.");
        return trying(event, compensationInfo, action);
    }

    /**
     * 请求header中存在tcc信息，返回分支事务，否则为新的主事务
     *
     * @return TccTransaction
     */
    @Transactional
    public <T> T trying(String event, String info, Supplier<T> tryLocalAction) {
        if (event == null || info == null || tryLocalAction == null) {
            throw new IllegalArgumentException("Args need not null.");
        }
        if (TCC_HOLDER.get() != null) {
            throw new IllegalStateException("Create new tccTransaction but already exists.");
        }
        TccContext tccContext = getTccContext();
        TccTransaction tccTransaction = new TccTransaction();
        if (tccContext != null) {
            tccTransaction.setTccTxId(tccContext.getTccTxId());
            tccTransaction.setPhase(tccContext.getPhase());
            tccTransaction.setType(TccTransaction.Type.BRANCH);
            tccTransaction.setCompensationEvent(event);
            tccTransaction.setCompensationInfo(info);
        } else {
            tccTransaction.setTccTxId(UUID.randomUUID().toString());
            tccTransaction.setPhase(TccTransaction.Phase.TRYING);
            tccTransaction.setType(TccTransaction.Type.ROOT);
            tccTransaction.setCompensationEvent(event);
            tccTransaction.setCompensationInfo(info);
        }
        LOGGER.debug("TccMgr saving context, TccTxId: {}", tccTransaction.getTccTxId());
        tccTransactionMapper.save(tccTransaction);
        TCC_HOLDER.set(tccTransaction);
        LOGGER.info("Trying with TccTxId: {}", tccTransaction.getTccTxId());
        return tryLocalAction.get();
    }

    private TccContext getTccContext() {
        return JsonMapper.load(getRequest().getHeader(TCC_HEADER), TccContext.class);
    }

    /**
     * <li>1.更改为confirming状态(new tx)</li>
     * <li>2.执行confirm操作</li>
     * <li>3.删事务表</li>
     */
    @Transactional
    public void confirm(Runnable confirmAction) {
        tccTransactionManager.confirming();
        LOGGER.debug("Tcc status: confirming, tccTxId: {}", extractTccTxId().orElse(null));
        confirmAction.run();
        tccTransactionManager.done();
    }

    /**
     * 进入confirming状态，表明本地和远程分支事务均try成功
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void confirming() {
        TccTransaction tccTransaction = TCC_HOLDER.get();
        if (tccTransaction == null) {
            tccTransaction = tccTransactionMapper.getByTccTxId(extractTccTxId().get()).orElseThrow(
                    () -> new IllegalStateException("No tccTransaction while do confirming."));
        }
        tccTransaction.setPhase(TccTransaction.Phase.CONFIRMING);
        tccTransactionMapper.update(tccTransaction);
    }

    /**
     * <li>1.更改为canceling状态(new tx)</li>
     * <li>2.执行cancel操作</li>
     * <li>3.删事务表</li>
     */
    @Transactional
    public void cancel(Runnable cancelAction) {
        if (needCancel()) {
            tccTransactionManager.canceling();
            LOGGER.debug("Tcc status: canceling, tccTxId: {}", extractTccTxId().orElse(null));
            cancelAction.run();
            tccTransactionManager.done();
        }
    }

    /**
     * 更新记录为canceling状态
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void canceling() {
        TccTransaction tccTransaction = TCC_HOLDER.get();
        if (tccTransaction == null) {
            tccTransaction = tccTransactionMapper.getByTccTxId(extractTccTxId().get()).orElseThrow(
                    () -> new IllegalStateException("No tccTransaction while do confirming."));
        }
        tccTransaction.setPhase(TccTransaction.Phase.CANCELING);
        tccTransactionMapper.update(tccTransaction);
    }

    /**
     * 有事务记录，且状态不为confirming表示需要执行操作
     *
     * @return bool
     */
    private boolean needCancel() {
        String tccTxId = extractTccTxId().orElseThrow(() -> new IllegalStateException("Cancel tx but have no tccTxId"));
        Optional<TccTransaction> txxTxOpt = tccTransactionMapper.getByTccTxId(tccTxId);
        return (txxTxOpt.isPresent())
                && !TccTransaction.Phase.CONFIRMING.equals(txxTxOpt.map(TccTransaction::getPhase).orElse(null));
    }

    /**
     * 从上下午获取TccTransaction，或者是分支事务从header获取TccContext
     *
     * @return tccTxId
     */
    private Optional<String> extractTccTxId() {
        TccTransaction tccTransaction = TCC_HOLDER.get();
        if (tccTransaction == null) {
            TccContext tccContext = TCC_CONTEXT_HOLDER.get();
            return Optional.ofNullable(tccContext).map(TccContext::getTccTxId);
        } else {
            return Optional.ofNullable(tccTransaction.getTccTxId());
        }
    }

    /**
     * confirm或cancel成功后删除事务表的记录
     */
    public void done() {
        String tccTxId = extractTccTxId().orElseThrow(() ->
                new IllegalStateException("No tccTransaction while tx done."));
        tccTransactionMapper.delete(tccTxId);
        TCC_HOLDER.remove();
    }

}
