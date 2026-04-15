package com.lucas.gym_management.gym.application.ports.outbound.repository;

import com.lucas.gym_management.gym.application.domain.model.Gym;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GymRepository {
    Gym save(Gym gym);

    Optional<Gym> findById(UUID id);

    List<Gym> findAll();

    void deleteById(UUID id);
}
