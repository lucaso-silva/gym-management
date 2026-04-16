package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.domain.model.valueObjects.GymAddress;
import com.lucas.gym_management.gym.application.dto.GymOutput;
import com.lucas.gym_management.gym.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gym.application.ports.inbound.update.UpdateGymInput;
import com.lucas.gym_management.gym.application.ports.inbound.update.UpdateGymUseCase;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UpdateGymUseCaseImpl implements UpdateGymUseCase {

    private final GymRepository gymRepository;

    @Override
    public GymOutput updateGym(UUID userId, UUID gymId, UpdateGymInput input) {
        //TODO: check if userID is gym manager

        var gym = gymRepository.findById(gymId)
                .orElseThrow(()-> new GymNotFoundException("Gym not found with id: %s".formatted(gymId)));

        applyUpdates(gym, input);

        return GymOutput.from(gymRepository.save(gym));
    }

    private void applyUpdates(Gym gym, UpdateGymInput input) {
        if(input.name() != null) {
            gym.renameTo(input.name());
        }

        if(input.phone() != null) {
            gym.updatePhone(input.phone());
        }

        if(input.address() != null) {

            gym.updateAddress(GymAddress.newAddress(input.address().street(),
                    input.address().number(),
                    input.address().neighborhood(),
                    input.address().city(),
                    input.address().state()));
        }
    }
}
