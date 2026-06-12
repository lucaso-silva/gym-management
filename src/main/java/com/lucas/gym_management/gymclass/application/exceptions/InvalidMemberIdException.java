package com.lucas.gym_management.gymclass.application.exceptions;

public class InvalidMemberIdException extends BusinessException {

    private static final String CODE = "gym-class.invalid-member-id";
    private static final Integer HTTP_STATUS = 409;

    public InvalidMemberIdException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
