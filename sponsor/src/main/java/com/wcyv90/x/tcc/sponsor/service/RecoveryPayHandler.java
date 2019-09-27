package com.wcyv90.x.tcc.sponsor.service;

import com.wcyv90.x.tcc.common.JsonMapper;
import com.wcyv90.x.tcc.sponsor.domain.model.PayInfo;
import com.wcyv90.x.tcc.tx.core.recovery.RecoveryHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RecoveryPayHandler implements RecoveryHandler {

    static final String PAY_EVENT = "pay_event";

    @Autowired
    PayServiceImpl payService;

    @Override
    public String getCompensationEvent() {
        return PAY_EVENT;
    }

    @Override
    public void confirm(String compensationInfo) {
        log.info("Recovery confirm pay_event");
        payService.confirmPay(JsonMapper.load(compensationInfo, PayInfo.class));

    }

    @Override
    public void cancel(String compensationInfo) {
        log.info("Recovery cancel pay_event");
        payService.cancelPay(JsonMapper.load(compensationInfo, PayInfo.class));
    }
}
