package com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.response;

import java.util.UUID;

public record ListUserResponse(UUID id,
                               String name,
                               String email) {
}
