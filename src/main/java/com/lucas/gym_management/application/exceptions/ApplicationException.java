package com.lucas.gym_management.application.exceptions;

public class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }
    public ApplicationException(String message, Throwable cause) {
        super(message, cause, true, false);
    }
}
