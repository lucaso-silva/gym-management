package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.dto.GymOutput;
import com.lucas.gym_management.gym.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gym.application.ports.inbound.manage_gym_classes.AddGymClassInput;
import com.lucas.gym_management.gym.application.ports.inbound.manage_gym_classes.AddGymClassUseCase;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AddGymClassUseCaseImpl implements AddGymClassUseCase {

    private final GymRepository gymRepository;

    @Override
    public GymOutput addGymClass(UUID loggedInUserId, UUID gymId, AddGymClassInput input) {
        //TODO: check if loggedInUserId is gym manager
        var gym = gymRepository.findById(gymId)
                .orElseThrow(()-> new GymNotFoundException("Gym not found with id: %s".formatted(gymId)));

        //TODO: check if gymClassId exists
        gym.addGymClass(input.gymClassId());
        var saved = gymRepository.save(gym);

        return GymOutput.from(saved);
    }
}