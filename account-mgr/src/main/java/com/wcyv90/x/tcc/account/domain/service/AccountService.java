package com.wcyv90.x.tcc.account.domain.service;

import com.wcyv90.x.tcc.account.domain.model.PayAccountInfo;

public interface AccountService {

    void tryPay(PayAccountInfo payAccountInfo);

    void confirmPay(PayAccountInfo payAccountInfo);

    void cancelPay(PayAccountInfo payAccountInfo);

}
