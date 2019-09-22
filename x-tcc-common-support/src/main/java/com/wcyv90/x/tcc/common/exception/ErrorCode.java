package com.wcyv90.x.tcc.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {



    ;

    private long code = 0;

    private String message;

    private HttpStatus httpStatus;

    ErrorCode(long code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
