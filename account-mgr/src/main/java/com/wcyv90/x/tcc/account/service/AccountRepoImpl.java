package com.wcyv90.x.tcc.account.service;

import com.wcyv90.x.tcc.account.domain.model.Account;
import com.wcyv90.x.tcc.account.domain.service.AccountRepo;
import com.wcyv90.x.tcc.account.service.mapper.AccountMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountRepoImpl implements AccountRepo {

    private final AccountMapper accountMapper;

    public AccountRepoImpl(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public Optional<Account> getById(Long id) {
        return accountMapper.getById(id);
    }

    @Override
    public void update(Account account) {
        accountMapper.update(account);
    }
}
