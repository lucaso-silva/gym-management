package com.lucas.gym_management.application.ports.inbound.get;

import com.lucas.gym_management.application.domain.model.Address;

public record GetUserOutput(
        String name,
        String email,
        String login,
        String phone,
        Address address
) {
}
