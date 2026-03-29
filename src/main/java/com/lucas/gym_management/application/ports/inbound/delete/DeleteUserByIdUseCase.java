package com.lucas.gym_management.application.ports.inbound.delete;

import java.util.UUID;

public interface DeleteUserByIdUseCase {

    void deleteUserById(UUID loggedInUserId, UUID id);
}
