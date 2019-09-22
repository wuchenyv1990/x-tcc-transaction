package com.wcyv90.x.tcc.account.service;

import com.wcyv90.x.tcc.account.domain.model.Account;
import com.wcyv90.x.tcc.account.domain.model.PayAccountInfo;
import com.wcyv90.x.tcc.account.domain.service.AccountManager;
import com.wcyv90.x.tcc.account.domain.service.AccountRepo;
import com.wcyv90.x.tcc.common.exception.AppException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountManagerImpl implements AccountManager {

    private final AccountRepo accountRepo;

    public AccountManagerImpl(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Transactional
    @Override
    public void tryPay(PayAccountInfo payAccountInfo) {
        Account account = accountRepo.getById(payAccountInfo.getAccountId())
                .orElseThrow(AppException::new);
        BigDecimal totalAmount = account.getTotalAmount();
        if (totalAmount.compareTo(payAccountInfo.getAmount()) < 0) {
            throw new AppException();
        }
        account.setTotalAmount(totalAmount.subtract(payAccountInfo.getAmount()));
        accountRepo.update(account);
    }

    @Transactional
    @Override
    public void cancelPay(PayAccountInfo payAccountInfo) {
        Account account = accountRepo.getById(payAccountInfo.getAccountId())
                .orElseThrow(AppException::new);
        account.setTotalAmount(account.getTotalAmount().add(payAccountInfo.getAmount()));
        accountRepo.update(account);
    }

    @Override
    public void confirmPay(PayAccountInfo payAccountInfo) {

    }
}
