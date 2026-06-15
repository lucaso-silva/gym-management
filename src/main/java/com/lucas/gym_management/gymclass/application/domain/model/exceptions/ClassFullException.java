package com.lucas.gym_management.gymclass.application.domain.model.exceptions;

public class ClassFullException extends DomainException {

    private static final String CODE = "gym-class.full-capacity";
    private static final Integer HTTP_STATUS = 409;

    public ClassFullException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
