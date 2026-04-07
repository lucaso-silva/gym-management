package com.lucas.gym_management.user.application.exceptions;

public class NotAuthorizedException extends ApplicationException {
    public NotAuthorizedException(String message) {
        super(message);
    }
}
