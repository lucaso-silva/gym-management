package com.lucas.gym_management.gym.application.domain.model.exceptions;

public class GymClassNotAssociatedException extends NotFoundException {
    public GymClassNotAssociatedException(String message) {
        super(message);
    }
}
