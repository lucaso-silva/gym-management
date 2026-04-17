package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.dto.GymOutput;
import com.lucas.gym_management.gym.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gym.application.ports.inbound.manage_members.AddMemberUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.manage_members.AddMemberInput;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AddMemberUseCaseImpl implements AddMemberUseCase {

    private final GymRepository gymRepository;

    @Override
    public GymOutput addMember(UUID loggedInUserId,
                               UUID gymId,
                               AddMemberInput memberId) {
        //TODO: check if loggedInUserId is gym manager

        var gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new GymNotFoundException("Gym not found with id: %s".formatted(gymId)));

        //TODO: check if memberId exists
        gym.addMember(memberId.memberId());
        var saved = gymRepository.save(gym);

        return GymOutput.from(saved);
    }
}
