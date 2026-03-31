package com.lucas.gym_management.application.ports.inbound.update;

import java.util.UUID;

public interface UpdateUserUseCase {
    UpdateUserOutput updateUser(UUID loggedInUserId, UUID userId, UpdateUserInput userInput);
}
