package com.lucas.gym_management.gymclass.application.ports.inbound.delete;

import java.util.UUID;

public interface DeleteGymClassUseCase {
    void deleteGymClassById(UUID id);
}
