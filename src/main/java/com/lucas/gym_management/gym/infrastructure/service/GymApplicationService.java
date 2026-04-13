package com.lucas.gym_management.gym.infrastructure.service;

import com.lucas.gym_management.gym.application.dto.GymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymInput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.get.GetGymByIdUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.list.ListGymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.list.ListGymsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GymApplicationService {
    private final CreateGymUseCase createGymUseCase;
    private final GetGymByIdUseCase getGymByIdUseCase;
    private final ListGymsUseCase listGymsUseCase;

    @Transactional
    public CreateGymOutput createGym(CreateGymInput input) {
        return createGymUseCase.createGym(input);
    }

    @Transactional(readOnly = true)
    public GymOutput getGymById(UUID id) {
        return getGymByIdUseCase.getGymById(id);
    }

    public List<ListGymOutput> listGyms() {
        return listGymsUseCase.listGyms();
    }
}
