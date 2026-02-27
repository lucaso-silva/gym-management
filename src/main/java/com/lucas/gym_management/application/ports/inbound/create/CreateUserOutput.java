package com.lucas.gym_management.application.ports.inbound.create;

import com.lucas.gym_management.application.domain.model.User;

import java.util.UUID;

public record CreateUserOutput(
        UUID id,
        String name,
        String email,
        String login
) {
    public static CreateUserOutput from(final User input) {
        return new CreateUserOutput(input.getId(),
                input.getName(),
                input.getEmail(),
                input.getLogin());
    }
}
