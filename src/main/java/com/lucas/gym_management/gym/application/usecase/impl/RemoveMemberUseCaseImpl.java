package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.dto.GymOutput;
import com.lucas.gym_management.gym.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gym.application.ports.inbound.manage_members.RemoveMemberUseCase;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import com.lucas.gym_management.gym.application.usecase.validator.MemberRemovalValidator;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class RemoveMemberUseCaseImpl implements RemoveMemberUseCase {
    private final GymRepository gymRepository;
    private final MemberRemovalValidator memberRemovalValidator;

    @Override
    public GymOutput removeMember(UUID loggedInUserId, UUID gymId, UUID memberId) {
        //TODO: check if LoggedInUserId is gym manager - JWT
        var gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new GymNotFoundException("Gym not found with id: %s".formatted(gymId)));

        memberRemovalValidator.verifyMemberRemoval(gym, memberId);

        gym.removeMember(memberId);
        var saved = gymRepository.save(gym);

        return GymOutput.from(saved);
    }
}
