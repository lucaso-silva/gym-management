package com.lucas.gym_management.gym.application.domain.model.exceptions;

public class RequiredFieldException extends DomainException{

    private static final String CODE = "gym.required-field";
    private static final Integer HTTP_STATUS = 400;

    public RequiredFieldException(String message){
        super(CODE, message, HTTP_STATUS);
    }
}
