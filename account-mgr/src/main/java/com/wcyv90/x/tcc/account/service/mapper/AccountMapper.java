package com.wcyv90.x.tcc.account.service.mapper;

import com.wcyv90.x.tcc.account.domain.model.Account;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface AccountMapper {

    Optional<Account> getByIdForUpdate(Long id);

    void update(@Param("account") Account account);

}
