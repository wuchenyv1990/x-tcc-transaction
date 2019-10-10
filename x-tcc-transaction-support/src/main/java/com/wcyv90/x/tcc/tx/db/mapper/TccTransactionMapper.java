package com.wcyv90.x.tcc.tx.db.mapper;

import com.wcyv90.x.tcc.tx.core.TccTransaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface TccTransactionMapper {

    Optional<TccTransaction> getByTccTxIdForUpdate(String tccTxId);

    void save(@Param("tccTx") TccTransaction tccTransaction);

    void update(@Param("tccTx") TccTransaction tccTransaction);

    void delete(String tccTxId);

    List<TccTransaction> getRootTccTransactions();

}
