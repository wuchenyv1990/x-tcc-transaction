package com.wcyv90.x.tcc.account.service;

import com.wcyv90.x.tcc.account.domain.model.Account;
import com.wcyv90.x.tcc.account.domain.model.PayAccountInfo;
import com.wcyv90.x.tcc.account.domain.service.AccountManager;
import com.wcyv90.x.tcc.account.domain.service.AccountRepo;
import com.wcyv90.x.tcc.common.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.wcyv90.x.tcc.common.util.ExceptionGenerator.probablyThrow;

@Service
@Slf4j
public class AccountManagerImpl implements AccountManager {

    @Autowired
    private AccountRepo accountRepo;

    @Transactional
    @Override
    public void tryPay(PayAccountInfo payAccountInfo) {
        log.info("Try: accountId={}, amount = {}",
                payAccountInfo.getAccountId(),
                payAccountInfo.getAmount()
        );
        probablyThrow();
        Account account = accountRepo.getById(payAccountInfo.getAccountId())
                .orElseThrow(AppException::new);
        BigDecimal totalAmount = account.getTotalAmount();
        if (totalAmount.compareTo(payAccountInfo.getAmount()) < 0) {
            throw new AppException();
        }
        BigDecimal subtractedAmount = totalAmount.subtract(payAccountInfo.getAmount());
        log.info("set account {} -> {}", account.getTotalAmount(), subtractedAmount);

        account.setTotalAmount(subtractedAmount);
        accountRepo.update(account);
        log.info("Try success.");
    }

    @Transactional
    @Override
    public void cancelPay(PayAccountInfo payAccountInfo) {
        log.info("Cancel: accountId={}, amount = {}",
                payAccountInfo.getAccountId(),
                payAccountInfo.getAmount()
        );
        probablyThrow();
        Account account = accountRepo.getById(payAccountInfo.getAccountId())
                .orElseThrow(AppException::new);
        BigDecimal addAmount = account.getTotalAmount().add(payAccountInfo.getAmount());
        log.info("cancel account {} -> {}", account.getTotalAmount(), addAmount);

        account.setTotalAmount(addAmount);
        accountRepo.update(account);
        log.info("Cancel success.");
    }

    @Override
    public void confirmPay(PayAccountInfo payAccountInfo) {
        log.info("Confirm: accountId={}, amount = {}",
                payAccountInfo.getAccountId(),
                payAccountInfo.getAmount()
        );
        probablyThrow();
    }
}
