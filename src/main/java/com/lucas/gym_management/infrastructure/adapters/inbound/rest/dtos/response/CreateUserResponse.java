package com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.response;

import java.util.UUID;

public record CreateUserResponse(UUID id,
                                 String name,
                                 String login,
                                 String email) {
}
