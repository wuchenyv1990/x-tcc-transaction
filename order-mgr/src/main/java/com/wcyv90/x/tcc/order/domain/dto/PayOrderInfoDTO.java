package com.wcyv90.x.tcc.order.domain.dto;

import com.wcyv90.x.tcc.order.domain.model.PayOrderInfo;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class PayOrderInfoDTO {

    Long orderId;

    /**
     * 付款
     */
    BigDecimal amount;

    public PayOrderInfo to() {
        return PayOrderInfo.builder().orderId(orderId).amount(amount).build();
    }

}
