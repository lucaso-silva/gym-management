package com.lucas.gym_management.gym.application.ports.inbound.manage_members;

import com.lucas.gym_management.gym.application.dto.GymOutput;

import java.util.UUID;

public interface RemoveMemberUseCase {
    GymOutput removeMember(UUID loggedInUserId,
                           UUID gymId,
                           UUID memberId);
}
