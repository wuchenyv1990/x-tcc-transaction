package com.wcyv90.x.tcc.order.domain.dto;

import com.wcyv90.x.tcc.order.domain.model.PayInfo;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class PayInfoDTO {

    String orderId;

    BigDecimal amount;

    public PayInfo to() {
        return PayInfo.builder().orderId(orderId).amount(amount).build();
    }

}
