package com.lucas.gym_management.gym.application.exceptions;

public class MemberInUseException extends ApplicationException {

    private static final String CODE = "gym.active-member";
    private static final Integer HTTP_STATUS = 409;

    public MemberInUseException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
