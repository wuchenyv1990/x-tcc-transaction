package com.wcyv90.x.tcc.account.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PayAccountInfo {

    Long accountId;

    BigDecimal amount;

}
