package com.lucas.gym_management.user.application.ports.inbound.delete;

import java.util.UUID;

public interface DeleteUserUseCase {

    void deleteUserById(UUID loggedInUserId, UUID id);
}
