package com.lucas.gym_management.gymclass.application.exceptions;

public class ActionNotAllowedException extends BusinessException {
    private static final String CODE = "gym-class.action-not-allowed";
    private static final Integer HTTP_STATUS = 409;

    public ActionNotAllowedException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
