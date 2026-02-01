package com.lucas.gym_management.application.ports.inbound.delete;

import java.util.UUID;

public interface ForDeletingUserById {

    void deleteUserById(UUID id);
}
