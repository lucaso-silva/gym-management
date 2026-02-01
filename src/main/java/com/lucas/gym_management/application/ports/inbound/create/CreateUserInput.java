package com.lucas.gym_management.application.ports.inbound.create;

import com.lucas.gym_management.application.domain.model.Address;

public record CreateUserInput(
        String name,
        String email,
        String login,
        String password,
        String phone,
        Address address
) {
}
