package com.wcyv90.x.tcc.sponsor.service;

import com.wcyv90.x.tcc.sponsor.domain.model.PayInfo;
import com.wcyv90.x.tcc.sponsor.domain.service.PayManager;
import com.wcyv90.x.tcc.sponsor.domain.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    PayManager payManager;

    @Override
    public boolean pay(PayInfo payInfo) {
        try {
            payManager.tryPay(payInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
