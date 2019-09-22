package com.wcyv90.x.tcc.account.domain.service;

import com.wcyv90.x.tcc.account.domain.model.Account;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface AccountRepo {

    Optional<Account> getById(Long id);

    void update(@Param("account") Account account);


}
