package com.lucas.gym_management.gym.application.domain.model.exceptions;

public class MemberAlreadyExistsException extends DomainException {
    private static final String CODE = "gym.member-already-exists";
    private static final Integer HTTP_STATUS = 409;

    public MemberAlreadyExistsException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
