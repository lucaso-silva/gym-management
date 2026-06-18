package com.lucas.gym_management.gym.application.exceptions;

public class ActiveMembershipException extends ApplicationException {

    private static final String CODE = "gym.active-membership";
    private static final Integer HTTP_STATUS = 409;

    public ActiveMembershipException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
