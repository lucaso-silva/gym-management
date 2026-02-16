package com.lucas.gym_management.application.domain.command;

import com.lucas.gym_management.application.domain.model.valueObjects.Address;

import java.time.LocalDate;

public record UpdateUserData(
        String name,
        String email,
        String phone,
        Address address,
        String gymName,
        String cref,
        String specialty,
        LocalDate birthDate,
        Boolean activeMembership
) {
}
