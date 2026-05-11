package com.lucas.gym_management.gym.application.ports.inbound.update;

import com.lucas.gym_management.gym.application.dto.GymAddressDTO;

public record UpdateGymInput(String name,
                             String phone,
                             GymAddressDTO address) {
}