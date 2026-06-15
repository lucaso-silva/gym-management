package com.lucas.gym_management.starter.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final String code;
    private final Integer httpStatus;

    public BaseException(String code, String message, Integer httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
