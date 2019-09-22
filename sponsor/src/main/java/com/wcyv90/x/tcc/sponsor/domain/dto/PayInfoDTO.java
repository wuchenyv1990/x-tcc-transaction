package com.wcyv90.x.tcc.sponsor.domain.dto;

import com.wcyv90.x.tcc.common.util.AmountDecimal;
import com.wcyv90.x.tcc.sponsor.domain.model.PayInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayInfoDTO {

    private Long orderId;

    private Long accountId;

    private String amount;

    public PayInfo to() {
        return PayInfo.builder()
                .accountId(accountId)
                .orderId(orderId)
                .amount(AmountDecimal.defaultPrecision(amount))
                .build();
    }

}
