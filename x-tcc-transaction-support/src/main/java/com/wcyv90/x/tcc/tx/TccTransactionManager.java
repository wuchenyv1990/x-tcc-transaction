package com.wcyv90.x.tcc.tx;

import com.wcyv90.x.tcc.common.JsonMapper;
import com.wcyv90.x.tcc.common.exception.AppException;

import java.util.UUID;

import static com.wcyv90.x.tcc.common.ServletUtil.getRequest;
import static com.wcyv90.x.tcc.tx.TccContext.TCC_HEADER;

public class TccTransactionManager {

    private static final ThreadLocal<TccTransaction> TCC_HOLDER = new InheritableThreadLocal<>();

    public static TccTransaction currentTccTx() {
        return TCC_HOLDER.get();
    }

    /**
     * 请求header中存在tcc信息，返回分支事务，否则为新的主事务
     *
     * @return
     */
    public static TccTransaction createTccTx() {
        if (TCC_HOLDER.get() != null) {
            throw new AppException();
        }
        TccContext tccContext = JsonMapper.load(getRequest().getHeader(TCC_HEADER), TccContext.class);
        TccTransaction tccTransaction = new TccTransaction();
        if (tccContext != null) {

            tccTransaction.setTccTxId(tccContext.getTccTxId());
            tccTransaction.setPhase(tccContext.getPhase());
            tccTransaction.setType(TccTransaction.Type.BRANCH);
        } else {
            tccTransaction.setTccTxId(UUID.randomUUID().toString());
            tccTransaction.setPhase(TccTransaction.Phase.TRYING);
            tccTransaction.setType(TccTransaction.Type.ROOT);
        }
        TCC_HOLDER.set(tccTransaction);
        return tccTransaction;
    }

}
