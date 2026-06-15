package com.lucas.gym_management.user.application.exceptions;

public class ConflictException extends ApplicationException {

    private static final String CODE = "user.conflict";
    private static final Integer HTTP_STATUS = 409;

    public ConflictException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
