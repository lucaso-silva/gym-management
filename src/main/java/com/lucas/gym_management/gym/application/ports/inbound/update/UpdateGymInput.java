package com.lucas.gym_management.gym.application.ports.inbound.update;

import com.lucas.gym_management.gym.application.dto.GymAddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateGymInput(@Size(min = 3, max = 75, message = "Gym name must be between 3 and 75 characters")
                             String name,
                             String phone,
                             UUID managerId,
                             @Valid
                             GymAddressDTO address) {
}