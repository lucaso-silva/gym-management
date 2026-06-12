package com.lucas.gym_management.gymclass.application.exceptions;

public class NotFoundException extends BusinessException {

    private static final String CODE = "gym-class.not-found";
    private static final Integer HTTP_STATUS = 404;

    public NotFoundException(String message)
    {
        super(CODE, message, HTTP_STATUS);
    }
}
