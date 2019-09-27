package com.wcyv90.x.tcc.tx.db;

import com.wcyv90.x.tcc.tx.core.TccTransaction;
import com.wcyv90.x.tcc.tx.db.mapper.TccTransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TccTransactionRepo {

    @Autowired
    TccTransactionMapper tccTransactionMapper;

    @Transactional(readOnly = true)
    public List<TccTransaction> getUncompensatedTxs() {
        return tccTransactionMapper.getRootTccTransactions();
    }

    @Transactional
    public void updateTccTransaction(TccTransaction tccTransaction) {
        tccTransactionMapper.update(tccTransaction);
    }

}
