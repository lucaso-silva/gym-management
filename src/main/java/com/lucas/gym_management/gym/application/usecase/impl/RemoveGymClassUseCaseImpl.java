package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.dto.GymOutput;
import com.lucas.gym_management.gym.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gym.application.ports.inbound.manage_gym_classes.RemoveGymClassUseCase;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class RemoveGymClassUseCaseImpl implements RemoveGymClassUseCase {

    private final GymRepository gymRepository;

    @Override
    public GymOutput removeGymClass(UUID loggedInUserId, UUID gymId, UUID gymClass) {
        //TODO: check if logged in user is gym manager
        var gym = gymRepository.findById(gymId)
                .orElseThrow(()-> new GymNotFoundException("Gym not found with id: %s".formatted(gymId)));

        gym.removeGymClass(gymClass);
        var saved = gymRepository.save(gym);

        return GymOutput.from(saved);
    }
}
