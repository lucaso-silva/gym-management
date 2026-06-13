package com.lucas.gym_management.gym.application.domain.model.exceptions;

public class NotFoundException extends DomainException {

    private static final String CODE = "gym.not-found";
    private static final Integer HTTP_STATUS = 404;

    public NotFoundException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
