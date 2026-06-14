package com.lucas.gym_management.gymclass.application.exceptions;

import com.lucas.gym_management.starter.exception.BaseException;
import lombok.Getter;

@Getter
public class ApplicationException extends BaseException {

    public ApplicationException(String code, String message, Integer httpStatus){
        super(code, message, httpStatus);
    }
}
