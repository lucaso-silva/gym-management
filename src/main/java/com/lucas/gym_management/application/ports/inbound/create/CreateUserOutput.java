package com.lucas.gym_management.application.ports.inbound.create;

public record CreateUserOutput(
        String name,
        String email,
        String login
) {
}
