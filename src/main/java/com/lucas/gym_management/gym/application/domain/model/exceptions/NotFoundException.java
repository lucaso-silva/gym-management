package com.lucas.gym_management.gym.application.domain.model.exceptions;

public class NotFoundException extends DomainException {
    public NotFoundException(String message) {
        super(message);
    }
}
