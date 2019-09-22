package com.wcyv90.x.tcc.account.domain.service;

import com.wcyv90.x.tcc.account.domain.model.PayAccountInfo;

public interface AccountManager {

    void tryPay(PayAccountInfo payAccountInfo);

    void cancelPay(PayAccountInfo payAccountInfo);

    void confirmPay(PayAccountInfo payAccountInfo);

}
