package com.lucas.gym_management.gym.application.ports.inbound.create;

import com.lucas.gym_management.gym.application.dto.GymAddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateGymInput(String name,
                             String phone,
                             @Valid @NotNull GymAddressDTO address) {
}
