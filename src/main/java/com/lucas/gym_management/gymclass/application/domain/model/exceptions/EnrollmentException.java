package com.lucas.gym_management.gymclass.application.domain.model.exceptions;

public class EnrollmentException extends DomainException {

    private static final String CODE = "gym-class.enrollment-error";
    private static final Integer HTTP_STATUS = 409;

    public EnrollmentException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
