package com.lucas.gym_management.gym.application.ports.outbound.repository;

import com.lucas.gym_management.gym.application.domain.model.Gym;

public interface GymRepository {
    Gym save(Gym gym);
}
