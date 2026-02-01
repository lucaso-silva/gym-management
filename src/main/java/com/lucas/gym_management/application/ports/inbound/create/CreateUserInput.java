package com.lucas.gym_management.application.ports.inbound.create;

import com.lucas.gym_management.application.domain.model.Address;
import com.lucas.gym_management.application.domain.model.UserType;

import java.util.Date;

public record CreateUserInput(
        UserType userType,
        String name,
        String email,
        String login,
        String password,
        String phone,
        Address address,

        String gymName,
        String cref,
        String specialty,
        Date birthDate,
        Boolean active
) {
}
