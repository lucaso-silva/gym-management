package com.lucas.gym_management.application.ports.inbound.create;

public record AddressDTO(
        String street,
        int number,
        String neighborhood,
        String zipCode,
        String city,
        String state
) {
}
