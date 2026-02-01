package com.lucas.gym_management.application.ports.inbound.list;

public record ListUserOutput(
        String name,
        String email,
        String login
) {
}
