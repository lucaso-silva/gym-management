package com.lucas.gym_management.application.ports.inbound.update;

import com.lucas.gym_management.application.dto.AddressDTO;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateUserInput(
        UUID id,
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
