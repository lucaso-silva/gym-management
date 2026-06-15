package com.lucas.gym_management.user.application.exceptions;

public class ForbiddenOperationException extends ApplicationException {

    private static final String CODE = "user.forbidden";
    private static final Integer HTTP_STATUS = 403;

    public ForbiddenOperationException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
