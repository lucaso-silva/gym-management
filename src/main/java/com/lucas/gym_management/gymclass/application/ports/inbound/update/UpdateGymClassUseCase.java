package com.lucas.gym_management.gymclass.application.ports.inbound.update;

import com.lucas.gym_management.gymclass.application.dto.GymClassOutput;

import java.util.UUID;

public interface UpdateGymClassUseCase {
    GymClassOutput updateGymClass(UUID gymClassId, UpdateGymClassInput input);
}
