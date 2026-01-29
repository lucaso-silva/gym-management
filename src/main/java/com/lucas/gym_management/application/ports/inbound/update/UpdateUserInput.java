package com.lucas.gym_management.application.ports.inbound.update;

import com.lucas.gym_management.application.domain.model.Address;

import java.util.UUID;

public record UpdateUserInput(
        UUID id,
        String name,
        String email,
        String phone,
        Address address
) {
}
