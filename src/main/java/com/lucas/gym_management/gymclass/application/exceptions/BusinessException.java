package com.lucas.gym_management.gymclass.application.exceptions;

import com.lucas.gym_management.starter.exception.BaseException;
import lombok.Getter;

@Getter
public class BusinessException extends BaseException {

    public BusinessException(String code, String message, Integer httpStatus){
        super(code, message, httpStatus);
    }
}
