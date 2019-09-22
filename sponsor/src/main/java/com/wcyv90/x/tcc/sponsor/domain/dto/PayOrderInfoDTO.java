package com.wcyv90.x.tcc.sponsor.domain.dto;


import com.wcyv90.x.tcc.sponsor.domain.model.PayInfo;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PayOrderInfoDTO {

    Long orderId;

    /**
     * 付款
     */
    BigDecimal amount;

    public static PayOrderInfoDTO from(PayInfo payInfo) {
        return PayOrderInfoDTO.builder()
                .orderId(payInfo.getOrderId())
                .amount(payInfo.getAmount())
                .build();
    }

}
