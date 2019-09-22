package com.wcyv90.x.tcc.tx;

import com.wcyv90.x.tcc.common.JsonMapper;
import com.wcyv90.x.tcc.tx.db.mapper.TccTransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.wcyv90.x.tcc.common.ServletUtil.getRequest;
import static com.wcyv90.x.tcc.tx.TccContext.TCC_HEADER;

@Service
public class TccTransactionManager {

    private static final ThreadLocal<TccTransaction> TCC_HOLDER = new InheritableThreadLocal<>();

    @Autowired
    private TccTransactionMapper tccTransactionMapper;

    public static TccTransaction currentTccTx() {
        return TCC_HOLDER.get();
    }

    /**
     * 请求header中存在tcc信息，返回分支事务，否则为新的主事务
     * 主：事务表和本地数据操作成功为原子操作
     * 分支：同上
     *
     * @return TccTransaction
     */
    public TccTransaction createTccTx(String event, String info) {
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
        tccTransactionMapper.save(tccTransaction);
        TCC_HOLDER.set(tccTransaction);
        return tccTransaction;
    }

    private TccContext getTccContext() {
        return JsonMapper.load(getRequest().getHeader(TCC_HEADER), TccContext.class);
    }

    /**
     * 进入confirming状态，表明本地和远程分支事务均try成功
     * 主：分支均成功后调用，记录状态
     * ??分支：从数据库查询，若记录为try，当前数据操作和删除事务表原子成功；
     */
    public void confirming() {
        TccTransaction tccTransaction = TCC_HOLDER.get();
        if (tccTransaction == null) {
            throw new IllegalStateException("No tccTransaction while do confirming.");
        }
        tccTransaction.setPhase(TccTransaction.Phase.CONFIRMING);
        tccTransactionMapper.update(tccTransaction);
    }

    /**
     * 更新记录为canceling状态
     */
    public void canceling() {
        TccTransaction tccTransaction = TCC_HOLDER.get();
        if (tccTransaction == null) {
            throw new IllegalStateException("No tccTransaction while do canceling.");
        }
        tccTransaction.setPhase(TccTransaction.Phase.CANCELING);
        tccTransactionMapper.update(tccTransaction);
    }

    public boolean needCancel() {
        return extractTccTxId().isPresent()
                && tccTransactionMapper.getByTccTxId(extractTccTxId().get()).isPresent();
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

    private Optional<String> extractTccTxId() {
        TccTransaction tccTransaction = TCC_HOLDER.get();
        if (tccTransaction == null) {
            TccContext tccContext = getTccContext();
            return Optional.ofNullable(tccContext).map(TccContext::getTccTxId);
        } else {
            return Optional.ofNullable(tccTransaction.getTccTxId());
        }
    }

}
