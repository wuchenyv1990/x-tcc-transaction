package com.wcyv90.x.tcc.sponsor.service;

import com.wcyv90.x.tcc.sponsor.domain.dto.PayAccountInfoDTO;
import com.wcyv90.x.tcc.sponsor.domain.dto.PayOrderInfoDTO;
import com.wcyv90.x.tcc.sponsor.domain.model.PayInfo;
import com.wcyv90.x.tcc.sponsor.domain.service.PayManager;
import com.wcyv90.x.tcc.sponsor.service.client.AccountService;
import com.wcyv90.x.tcc.sponsor.service.client.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayManagerImpl implements PayManager {

    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    @Override
    public void tryPay(PayInfo payInfo) {
        orderService.tryPay(PayOrderInfoDTO.from(payInfo));
        accountService.tryPay(PayAccountInfoDTO.from(payInfo));
    }

    @Override
    public void confirmPay(PayInfo payInfo) {

    }

    @Override
    public void cancelPay(PayInfo payInfo) {

    }



}
