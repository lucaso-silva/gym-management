package com.lucas.gym_management.gym.application.exceptions;

public class ActiveMemberException extends ApplicationException {

    private static final String CODE = "gym.active-member";
    private static final Integer HTTP_STATUS = 409;

    public ActiveMemberException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
