package com.lucas.gym_management.gym.application.dto;

public record GymAddressDTO(String street,
                            String number,
                            String neighborhood,
                            String city,
                            String state) {
}
