package com.lucas.gym_management.gym.application.service;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.domain.model.valueObjects.GymAddress;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymInput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymUseCase;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateGymUseCaseImpl implements CreateGymUseCase {

    private GymRepository gymRepository;

    @Override
    @Transactional
    public CreateGymOutput createGym(CreateGymInput input) {
        var gymAddress = GymAddress.newAddress(input.address().street(),
                        input.address().number(),
                        input.address().neighborhood(),
                        input.address().city(),
                        input.address().state());

        var newGym = Gym.createNewGym(input.name(),
                input.phone(),
                gymAddress);

        return CreateGymOutput.from(gymRepository.save(newGym));
    }



}
