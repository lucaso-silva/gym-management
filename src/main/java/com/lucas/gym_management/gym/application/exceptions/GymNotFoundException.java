package com.lucas.gym_management.gym.application.exceptions;

public class GymNotFoundException extends ApplicationException {

    private static final String CODE = "gym.not-found";
    private static final Integer HTTP_STATUS = 404;

    public GymNotFoundException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
