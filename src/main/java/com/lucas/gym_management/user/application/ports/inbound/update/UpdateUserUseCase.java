package com.lucas.gym_management.user.application.ports.inbound.update;

import com.lucas.gym_management.user.application.dto.user.UserOutput;

import java.util.UUID;

public interface UpdateUserUseCase {
    UserOutput updateUser(UUID loggedInUserId, UUID userId, UpdateUserInput userInput);
}
