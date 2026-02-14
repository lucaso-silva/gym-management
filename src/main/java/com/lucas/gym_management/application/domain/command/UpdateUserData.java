package com.lucas.gym_management.application.domain.command;

import com.lucas.gym_management.application.domain.model.Address;

import java.util.Date;

public record UpdateUserData(
        String name,
        String email,
        String phone,
        Address address,
        String gymName,
        String cref,
        String specialty,
        Date birthDate,
        Boolean activeMembership
) {
}
