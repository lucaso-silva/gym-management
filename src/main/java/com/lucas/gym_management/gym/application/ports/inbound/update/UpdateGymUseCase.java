package com.lucas.gym_management.gym.application.ports.inbound.update;

import com.lucas.gym_management.gym.application.dto.GymOutput;

import java.util.UUID;

public interface UpdateGymUseCase {
    GymOutput updateGym(UUID userId, UUID gymId, UpdateGymInput input);
}
