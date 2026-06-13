package com.lucas.gym_management.user.application.domain.model.exceptions;

public class RequiredFieldException extends DomainException{

    private static final String CODE = "user.required-field";
    private static final Integer HTTP_STATUS = 400;

    public RequiredFieldException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
