package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.ports.inbound.list.ListGymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.list.ListGymsUseCase;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ListGymsUseCaseImpl implements ListGymsUseCase {
    private final GymRepository gymRepository;

    @Override
    public List<ListGymOutput> listGyms() {
        List<Gym> list = gymRepository.findAll();

        return list.stream()
                .map(ListGymOutput::from)
                .toList();
    }
}
