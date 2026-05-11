package com.lucas.gym_management.gym.application.ports.inbound.delete;

import java.util.UUID;

public interface DeleteGymUseCase {
    void deleteGymById(UUID userId, UUID id);
}
