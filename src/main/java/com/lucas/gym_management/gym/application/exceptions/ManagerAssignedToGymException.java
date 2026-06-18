package com.lucas.gym_management.gym.application.exceptions;

public class ManagerAssignedToGymException extends ApplicationException {
    private static final String CODE = "gym.assigned-manager";
    private static final Integer HTTP_STATUS = 409;

    public ManagerAssignedToGymException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
