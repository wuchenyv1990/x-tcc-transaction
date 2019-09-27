package com.wcyv90.x.tcc.sponsor.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayInfo {

    private Long orderId;

    private Long accountId;

    private BigDecimal amount;

}