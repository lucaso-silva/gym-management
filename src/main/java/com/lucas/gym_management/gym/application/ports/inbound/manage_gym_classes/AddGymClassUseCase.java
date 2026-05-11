package com.lucas.gym_management.gym.application.ports.inbound.manage_gym_classes;

import com.lucas.gym_management.gym.application.dto.GymOutput;

import java.util.UUID;

public interface AddGymClassUseCase {

    GymOutput addGymClass(UUID loggedInUserId,
                          UUID gymId,
                          AddGymClassInput gymClass);
}
