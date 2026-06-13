package com.lucas.gym_management.gym.application.exceptions;

public class InvalidMemberException extends ApplicationException {

    private static final String CODE = "gym.invalid-member";
    private static final Integer HTTP_STATUS = 404;

    public InvalidMemberException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
