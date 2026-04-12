package com.lucas.gym_management.gym.infrastructure.service;

import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymInput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class GymApplicationService {
    private final CreateGymUseCase createGymUseCase;

    @Transactional
    public CreateGymOutput createGym(CreateGymInput input) {
        return createGymUseCase.createGym(input);
    }
}
