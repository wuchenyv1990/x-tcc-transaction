package com.wcyv90.x.tcc.order.domain.dto;

import com.wcyv90.x.tcc.order.domain.model.PayOrderInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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
