package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.ports.inbound.list.ListGymClassOutput;
import com.lucas.gym_management.gymclass.application.ports.inbound.list.ListGymClassesUseCase;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ListGymClassesUseCaseImpl implements ListGymClassesUseCase {
    private final GymClassRepository gymClassRepository;

    @Override
    public List<ListGymClassOutput> listGymClasses() {
        return gymClassRepository.findAll()
                .stream()
                .map(ListGymClassOutput::from)
                .toList();
    }
}
