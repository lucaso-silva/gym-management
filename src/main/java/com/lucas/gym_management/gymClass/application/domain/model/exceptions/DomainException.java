package com.lucas.gym_management.gymclass.application.domain.model.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DomainException extends RuntimeException {
    private final String code;
    private final HttpStatus status;

    public DomainException(String message, String code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
