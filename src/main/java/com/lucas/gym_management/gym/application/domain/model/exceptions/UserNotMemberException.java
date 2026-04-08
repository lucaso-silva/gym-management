package com.lucas.gym_management.gym.application.domain.model.exceptions;

public class UserNotMemberException extends NotFoundException {
    public UserNotMemberException(String message) {
        super(message);
    }
}
