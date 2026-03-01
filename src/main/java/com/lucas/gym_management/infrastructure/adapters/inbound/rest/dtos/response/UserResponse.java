package com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.response;

import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.AddressRestDTO;

import java.util.UUID;

public record UserResponse(UUID id,
                           String name,
                           String email,
                           String login,
                           String phone,
                           AddressRestDTO address) {
}
