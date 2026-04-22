package com.lucas.gym_management.gym.application.ports.inbound.create;

import com.lucas.gym_management.gym.application.dto.GymAddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateGymInput(@NotBlank(message = "Name cannot be blank")
                             String name,
                             @NotBlank(message = "Phone cannot be blank")
                             String phone,
                             @Valid @NotNull(message = "Address cannot be null")
                             GymAddressDTO address) {
}
