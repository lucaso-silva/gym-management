package com.lucas.gym_management.user.application.exceptions;

public class NotAuthorizedException extends ApplicationException {

    private static final String CODE = "user.unauthorized";
    private static final Integer HTTP_STATUS = 401;

    public NotAuthorizedException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
