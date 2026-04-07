package com.lucas.gym_management.user.application.dto;

public record AddressDTO(
        String street,
        String number,
        String neighborhood,
        String zipCode,
        String city,
        String state
) {
}
