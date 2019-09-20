package com.wcyv90.x.tcc.order.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PayInfo {

    Long orderId;

    BigDecimal amount;

}
