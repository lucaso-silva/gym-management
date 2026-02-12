package com.lucas.gym_management.application.dto;

public record AddressDTO(
        String street,
        int number,
        String neighborhood,
        String zipCode,
        String city,
        String state
) {
}
