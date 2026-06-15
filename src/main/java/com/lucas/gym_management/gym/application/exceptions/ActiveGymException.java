package com.lucas.gym_management.gym.application.exceptions;

public class ActiveGymException extends ApplicationException {

    private static final String CODE = "gym.active-gym";
    private static final Integer HTTP_STATUS = 409;

    public ActiveGymException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
