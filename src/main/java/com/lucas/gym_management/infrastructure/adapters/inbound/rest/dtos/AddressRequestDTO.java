package com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos;

public record AddressRequestDTO(
        String street,
        int number,
        String neighborhood,
        String zipCode,
        String city,
        String state
) {
}
