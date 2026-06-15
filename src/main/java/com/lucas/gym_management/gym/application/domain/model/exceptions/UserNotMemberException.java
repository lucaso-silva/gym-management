package com.lucas.gym_management.gym.application.domain.model.exceptions;

public class UserNotMemberException extends DomainException {

    private static final String CODE = "gym.user-not-member";
    private static final Integer HTTP_STATUS = 409;

    public UserNotMemberException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
