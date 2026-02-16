package com.lucas.gym_management.application.ports.inbound.list;

import com.lucas.gym_management.application.domain.model.User;

import java.util.UUID;

public record ListUserOutput(
        UUID id,
        String name,
        String email
) {
    public static ListUserOutput from(User user) {

        return new ListUserOutput(user.getId(),
                user.getName(),
                user.getEmail());
    }
}
