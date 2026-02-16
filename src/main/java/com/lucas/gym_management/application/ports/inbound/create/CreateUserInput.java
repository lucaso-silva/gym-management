package com.lucas.gym_management.application.ports.inbound.create;

import com.lucas.gym_management.application.domain.model.UserType;
import com.lucas.gym_management.application.dto.AddressDTO;

import java.time.LocalDate;

public record CreateUserInput(
        UserType userType,
        String name,
        String email,
        String login,
        String password,
        String phone,
        AddressDTO address,
        String gymName,
        String cref,
        String specialty,
        LocalDate birthDate
) {
}
