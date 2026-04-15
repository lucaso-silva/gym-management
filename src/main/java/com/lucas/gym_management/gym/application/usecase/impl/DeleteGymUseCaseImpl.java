package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.ports.inbound.delete.DeleteGymUseCase;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import com.lucas.gym_management.user.application.exceptions.ApplicationException;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeleteGymUseCaseImpl implements DeleteGymUseCase {
    private final GymRepository gymRepository;

    @Override
    public void deleteGymById(UUID id) {
        var gym = gymRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gym not found with id %s".formatted(id)));

        if(!gym.getMembersIds().isEmpty()) {
            throw new ApplicationException("You cannot delete a gym with active members");
        }

        gymRepository.deleteById(id);
    }
}
