package com.lucas.gym_management.application.ports.inbound.create;

import com.lucas.gym_management.application.domain.model.User;

public record CreateUserOutput(
        String name,
        String email,
        String login
) {
    public static CreateUserOutput from(final User input) {
        return new CreateUserOutput(input.getName(),
                input.getEmail(),
                input.getLogin());
    }
}
