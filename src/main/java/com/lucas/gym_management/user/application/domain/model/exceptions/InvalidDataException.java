package com.lucas.gym_management.user.application.domain.model.exceptions;

public class InvalidDataException extends DomainException {

    private static final String CODE = "user.invalid-email-format";
    private static final Integer HTTP_STATUS = 400;

    public InvalidDataException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
