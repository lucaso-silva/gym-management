package com.lucas.gym_management.user.application.domain.model.exceptions;

import com.lucas.gym_management.starter.exception.BaseException;

public class DomainException extends BaseException {

    public DomainException(String code, String message, Integer httpStatus) {
        super(code, message, httpStatus);
    }
}
