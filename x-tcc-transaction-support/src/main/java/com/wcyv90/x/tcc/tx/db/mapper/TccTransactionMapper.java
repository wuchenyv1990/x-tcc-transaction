package com.wcyv90.x.tcc.tx.db.mapper;

import com.wcyv90.x.tcc.tx.TccTransaction;
import org.apache.ibatis.annotations.Param;

public interface TccTransactionMapper {

    void save(@Param("tccTx") TccTransaction tccTransaction);

    TccTransaction update(@Param("phase") TccTransaction.Phase phase, @Param("tccTxId")  String tccTxId);

    TccTransaction delete(String tccTxId);

}
