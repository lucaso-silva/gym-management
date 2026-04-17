package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.dto.GymOutput;
import com.lucas.gym_management.gym.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gym.application.ports.inbound.manage_members.RemoveMemberUseCase;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class RemoveMemberUseCaseImpl implements RemoveMemberUseCase {
    private final GymRepository gymRepository;

    @Override
    public GymOutput removeMember(UUID loggedInUserId, UUID gymId, UUID memberId) {
        //TODO: check if LoggedInUserId is gym manager

        var gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new GymNotFoundException("Gym not found with id: %s".formatted(gymId)));

        gym.removeMember(memberId);
        var saved = gymRepository.save(gym);

        return GymOutput.from(saved);
    }
}
