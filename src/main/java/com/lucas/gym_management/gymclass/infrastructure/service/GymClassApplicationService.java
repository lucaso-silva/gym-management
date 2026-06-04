package com.lucas.gym_management.gymclass.infrastructure.service;

import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassInput;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassOutput;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class GymClassApplicationService {
    private final CreateGymClassUseCase createGymClassUseCase;

    @Transactional
    public CreateGymClassOutput createGymClass(CreateGymClassInput input){
        return createGymClassUseCase.createGymClass(input);
    }
}
