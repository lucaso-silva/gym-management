package com.lucas.gym_management.application.ports.inbound.update;

import com.lucas.gym_management.application.domain.model.User;

import java.util.UUID;

public record UpdatedUserOutput(
        UUID id,
        String name
) {
    public static UpdatedUserOutput from(User user) {
        return new UpdatedUserOutput(
                user.getId(),
                user.getName());
    }
}
