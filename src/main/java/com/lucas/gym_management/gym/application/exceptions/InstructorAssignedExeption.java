package com.lucas.gym_management.gym.application.exceptions;

public class InstructorAssignedExeption extends ApplicationException {

    private static final String CODE = "gym.assigned-instructor";
    private static final Integer HTTP_STATUS = 409;

    public InstructorAssignedExeption(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
