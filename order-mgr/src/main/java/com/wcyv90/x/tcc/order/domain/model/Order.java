package com.wcyv90.x.tcc.order.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Order {

    private Long id;

    private BigDecimal priceAmount;

    private BigDecimal paidAmount;

}
