package com.lucas.gym_management.gymclass.application.domain.model.exceptions;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
