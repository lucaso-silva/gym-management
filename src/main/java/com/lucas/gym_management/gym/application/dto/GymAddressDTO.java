package com.lucas.gym_management.gym.application.dto;

import com.lucas.gym_management.gym.application.domain.model.valueObjects.GymAddress;
import jakarta.validation.constraints.NotBlank;

public record GymAddressDTO(@NotBlank(message = "Street cannot be blank")
                            String street,
                            @NotBlank(message = "Number cannot be blank")
                            String number,
                            @NotBlank(message = "Neighborhood cannot be balnk")
                            String neighborhood,
                            @NotBlank(message = "City cannot be blank")
                            String city,
                            @NotBlank(message = "State cannot be blank")
                            String state) {
    public static GymAddressDTO from(GymAddress address) {
        return new GymAddressDTO(address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState());
    }
}
