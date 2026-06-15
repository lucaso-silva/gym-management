package com.lucas.gym_management.gymclass.application.exceptions;

public class InvalidMemberException extends ApplicationException {

    private static final String CODE = "gym-class.invalid-member";
    private static final Integer HTTP_STATUS = 404;

    public InvalidMemberException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
