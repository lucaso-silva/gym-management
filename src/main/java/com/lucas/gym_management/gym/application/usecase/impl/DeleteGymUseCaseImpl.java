package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.exceptions.ApplicationException;
import com.lucas.gym_management.gym.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gym.application.ports.inbound.delete.DeleteGymUseCase;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeleteGymUseCaseImpl implements DeleteGymUseCase {
    private final GymRepository gymRepository;

    @Override
    public void deleteGymById(UUID userId,
                              UUID id) {
        //TODO: check if user is admin

        var gym = gymRepository.findById(id)
                .orElseThrow(() -> new GymNotFoundException("Gym not found with id %s".formatted(id)));

        if(!gym.getMembersIds().isEmpty()) {
            throw new ApplicationException("You cannot delete a gym with active members");
        }

        if(!gym.getGymClassesIds().isEmpty()) {
            throw new ApplicationException("You cannot delete a gym with active classes");
        }

        gymRepository.deleteById(id);
    }
}
