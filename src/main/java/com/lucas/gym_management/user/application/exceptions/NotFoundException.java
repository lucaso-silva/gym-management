package com.lucas.gym_management.user.application.exceptions;

public class NotFoundException extends ApplicationException {

    private static final String CODE = "user.not-found";
    private static final Integer HTTP_STATUS = 404;

    public NotFoundException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
