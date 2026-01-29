package com.lucas.gym_management.application.ports.inbound.update;

import com.lucas.gym_management.application.domain.model.Address;

public record UpdatedUserOutput(
        String name,
        String email,
        String phone,
        Address address
) {
}
