package com.lucas.gym_management.gymclass.application.domain.model.exceptions;

public class InvalidCapacityException extends DomainException {

    private static final String CODE = "gym-class.invalid-capacity";
    private static final Integer HTTP_STATUS = 409;

    public InvalidCapacityException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
