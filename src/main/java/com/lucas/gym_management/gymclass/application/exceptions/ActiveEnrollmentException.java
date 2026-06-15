package com.lucas.gym_management.gymclass.application.exceptions;

public class ActiveEnrollmentException extends ApplicationException {
    private static final String CODE = "gym-class.active-enrollments";
    private static final Integer HTTP_STATUS = 409;

    public ActiveEnrollmentException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
