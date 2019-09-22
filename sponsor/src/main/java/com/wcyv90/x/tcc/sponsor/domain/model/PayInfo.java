package com.wcyv90.x.tcc.sponsor.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PayInfo {

    private Long orderId;

    private Long accountId;

    private BigDecimal amount;

}