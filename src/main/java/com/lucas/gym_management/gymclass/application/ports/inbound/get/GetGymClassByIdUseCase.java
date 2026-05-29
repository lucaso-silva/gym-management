package com.lucas.gym_management.gymclass.application.ports.inbound.get;

import com.lucas.gym_management.gymclass.application.dto.GymClassOutput;

import java.util.UUID;

public interface GetGymClassByIdUseCase {
    GymClassOutput getGymClassById(UUID id);
}
