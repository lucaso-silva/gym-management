package com.lucas.gym_management.gymclass.application.domain.model.exceptions;

import com.lucas.gym_management.starter.exception.BaseException;
import lombok.Getter;

@Getter
public class DomainException extends BaseException {

    public DomainException(String code, String message, Integer httpStatus) {
        super(code, message, httpStatus);
    }
}
