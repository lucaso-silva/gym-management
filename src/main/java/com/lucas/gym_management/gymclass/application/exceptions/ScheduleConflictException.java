package com.lucas.gym_management.gymclass.application.exceptions;

public class ScheduleConflictException extends ApplicationException {

    private static final String CODE = "gym-class.schedule-conflict";
    private static final Integer HTTP_STATUS = 409;

    public ScheduleConflictException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
