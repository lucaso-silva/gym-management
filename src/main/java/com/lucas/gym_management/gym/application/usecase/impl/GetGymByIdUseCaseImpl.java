package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.dto.GymOutput;
import com.lucas.gym_management.gym.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gym.application.ports.inbound.get.GetGymByIdUseCase;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class GetGymByIdUseCaseImpl implements GetGymByIdUseCase {
    private final GymRepository gymRepository;

    @Override
    public GymOutput getGymById(UUID id) {
        var gym = gymRepository.findById(id);

        return gym.map(GymOutput::from)
                .orElseThrow(() -> new GymNotFoundException("Gym not found with id %s".formatted(id)));
    }
}
