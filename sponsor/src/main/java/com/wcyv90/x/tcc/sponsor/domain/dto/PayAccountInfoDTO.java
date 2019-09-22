package com.wcyv90.x.tcc.sponsor.domain.dto;


import com.wcyv90.x.tcc.sponsor.domain.model.PayInfo;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class PayAccountInfoDTO {

    Long accountId;

    /**
     * 扣款
     */
    BigDecimal amount;

    public static PayAccountInfoDTO from(PayInfo payInfo) {
        return PayAccountInfoDTO.builder()
                .accountId(payInfo.getAccountId())
                .amount(payInfo.getAmount())
                .build();
    }

}
