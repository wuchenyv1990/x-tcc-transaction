package com.wcyv90.x.tcc.account.service;

import com.wcyv90.x.tcc.account.domain.model.PayAccountInfo;
import com.wcyv90.x.tcc.account.domain.service.AccountManager;
import com.wcyv90.x.tcc.account.domain.service.AccountService;
import com.wcyv90.x.tcc.tx.core.TccTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountManager accountManager;

    @Autowired
    TccTransactionManager tccTransactionManager;

    @Override
    public void tryPay(PayAccountInfo payAccountInfo) {
        tccTransactionManager.branchTry(
                payAccountInfo,
                accountManager::tryPay
        );
    }

    @Override
    public void confirmPay(PayAccountInfo payAccountInfo) {
        tccTransactionManager.confirm(() -> accountManager.confirmPay(payAccountInfo));
    }

    @Override
    public void cancelPay(PayAccountInfo payAccountInfo) {
        tccTransactionManager.cancel(() -> accountManager.cancelPay(payAccountInfo));
    }
}
