package com.wcyv90.x.tcc.tx.db.itf;

import com.wcyv90.x.tcc.tx.TccTransaction;
import com.wcyv90.x.tcc.tx.db.mapper.TccTransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TccTransactionRepo {

    @Autowired
    TccTransactionMapper tccTransactionMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(TccTransaction tccTransaction) {
        tccTransactionMapper.save(tccTransaction);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(String tccTxId) {
        tccTransactionMapper.delete(tccTxId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(TccTransaction.Phase phase, String tccTxId) {
        tccTransactionMapper.update(phase, tccTxId);
    }

}
