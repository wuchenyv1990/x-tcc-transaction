package com.wcyv90.x.tcc.account.domain.dto;

import com.wcyv90.x.tcc.account.domain.model.PayAccountInfo;

import java.math.BigDecimal;

public class PayAccountInfoDTO {

    Long accountId;

    /**
     * 扣款
     */
    BigDecimal amount;

    public PayAccountInfo to() {
        return PayAccountInfo.builder().accountId(accountId).amount(amount).build();
    }

}
