package com.lucas.gym_management.user.application.ports.inbound.update;

import com.lucas.gym_management.user.application.dto.AddressDTO;

import java.time.LocalDate;

public record UpdateUserInput(
        String name,
        String email,
        String phone,
        AddressDTO address,
        String gymName,
        String cref,
        String specialty,
        LocalDate birthDate,
        Boolean activeMembership
) {
}
