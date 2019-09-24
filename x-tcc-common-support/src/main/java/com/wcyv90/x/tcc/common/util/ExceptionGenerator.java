package com.wcyv90.x.tcc.common.util;

import com.wcyv90.x.tcc.common.exception.AppException;

import java.util.Random;

public class ExceptionGenerator {

    private static final Random RANDOM = new Random();

    /**
     * 随机异常，制造各种场景
     */
    public static void probablyThrow() {
        if (RANDOM.nextInt(10) > 7) {
            throw new AppException("Random exception");
        }
    }

    public static void throwAppException() {
        throw new AppException("Generated exception");
    }

}
