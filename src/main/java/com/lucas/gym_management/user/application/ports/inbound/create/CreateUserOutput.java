package com.lucas.gym_management.user.application.ports.inbound.create;

import com.lucas.gym_management.user.application.domain.model.User;
import com.lucas.gym_management.user.application.domain.model.UserType;

import java.util.UUID;

public record CreateUserOutput(
        UUID id,
        String name,
        String email,
        String login,
        UserType userType
) {
    public static CreateUserOutput from(final User input) {
        return new CreateUserOutput(input.getId(),
                input.getName(),
                input.getEmail(),
                input.getLogin(),
                input.getUserType());
    }
}
