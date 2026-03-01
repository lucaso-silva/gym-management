package com.lucas.gym_management.application.ports.inbound.update;

import java.util.UUID;

public interface ForUpdateUser {
    UpdatedUserOutput updateUser(UUID id, UpdateUserInput userInput);
}
