package com.lucas.gym_management.gym.application.dto;

import com.lucas.gym_management.gym.application.domain.model.valueObjects.GymAddress;

public record GymAddressDTO(String street,
                            String number,
                            String neighborhood,
                            String city,
                            String state) {
    public static GymAddressDTO from(GymAddress address) {
        return new GymAddressDTO(address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState());
    }
}
