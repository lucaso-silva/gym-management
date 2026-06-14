package com.lucas.gym_management.gym.application.exceptions;

public class UserNotFoundException extends ApplicationException {

    private static final String CODE = "gym.invalid-member";
    private static final Integer HTTP_STATUS = 404;

    public UserNotFoundException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
