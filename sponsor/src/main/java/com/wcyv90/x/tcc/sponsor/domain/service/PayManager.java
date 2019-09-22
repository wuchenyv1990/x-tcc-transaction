package com.wcyv90.x.tcc.sponsor.domain.service;

import com.wcyv90.x.tcc.sponsor.domain.model.PayInfo;

public interface PayManager {

    void tryPay(PayInfo payInfo);

    void confirmPay(PayInfo payInfo);

    void cancelPay(PayInfo payInfo);

}
