package com.lucas.gym_management.gym.application.exceptions;

public class DuplicateAddressException extends ApplicationException {

    private static final String CODE = "gym.duplicate-address";
    private static final Integer HTTP_STATUS = 409;

    public DuplicateAddressException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
