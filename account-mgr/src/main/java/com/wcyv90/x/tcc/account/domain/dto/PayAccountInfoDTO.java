package com.wcyv90.x.tcc.account.domain.dto;

import com.wcyv90.x.tcc.account.domain.model.PayAccountInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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
