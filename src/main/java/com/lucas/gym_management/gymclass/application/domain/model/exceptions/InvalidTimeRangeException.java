package com.lucas.gym_management.gymclass.application.domain.model.exceptions;

public class InvalidTimeRangeException extends DomainException {

    private static final String CODE = "gym-class.invalid-time-range";
    private static final Integer HTTP_STATUS = 400;

    public InvalidTimeRangeException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
