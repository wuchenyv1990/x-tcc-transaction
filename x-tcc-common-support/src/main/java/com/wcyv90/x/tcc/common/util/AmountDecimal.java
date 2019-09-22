package com.wcyv90.x.tcc.common.util;

import com.wcyv90.x.tcc.common.exception.AppException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountDecimal {

    public static BigDecimal defaultPrecision(String amount) {
        if (!amount.matches("^(([1-9]\\d{1,16})|([0]))(\\.(\\d){0,2})?$")) {
            throw new AppException();
        }
        return new BigDecimal(amount).setScale(4, RoundingMode.HALF_UP);
    }

}
