package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.exceptions.NotFoundException;
import com.lucas.gym_management.gymclass.application.ports.inbound.get.GetGymClassByIdUseCase;
import com.lucas.gym_management.gymclass.application.dto.GymClassOutput;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class GetGymClassByIdUseCaseImpl implements GetGymClassByIdUseCase {
    private final GymClassRepository gymClassRepository;

    @Override
    public GymClassOutput getGymClassById(UUID id) {
        var gymClass = gymClassRepository.findById(id);

        return gymClass.map(GymClassOutput::from)
                .orElseThrow(() -> new NotFoundException("Gym class not found with id %s".formatted(id)));
    }
}
