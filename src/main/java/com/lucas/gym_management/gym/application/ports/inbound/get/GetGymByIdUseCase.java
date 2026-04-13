package com.lucas.gym_management.gym.application.ports.inbound.get;

import com.lucas.gym_management.gym.application.dto.GymOutput;

import java.util.UUID;

public interface GetGymByIdUseCase {
    GymOutput getGymById(UUID id);
}
