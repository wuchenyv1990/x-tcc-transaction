package com.wcyv90.x.tcc.account.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class Account {

    private Long id;

    private BigDecimal totalAmount;

    private LocalDateTime updateTime;

}
