package com.lucas.gym_management.user.application.domain.model.exceptions;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause, true, false);
    }
}
