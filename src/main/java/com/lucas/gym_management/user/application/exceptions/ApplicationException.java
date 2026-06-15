package com.lucas.gym_management.user.application.exceptions;

import com.lucas.gym_management.starter.exception.BaseException;

public class ApplicationException extends BaseException {
    public ApplicationException(String code, String message, Integer httpStatus) {
        super(code, message, httpStatus);
    }
}
