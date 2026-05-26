package com.lucas.gym_management.gymclass.application.ports.inbound.get;

import java.util.UUID;

public interface GetGymClassByIdUseCase {
    GymClassOutput getGymClassById(UUID id);
}
