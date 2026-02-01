package com.lucas.gym_management.application.ports.inbound.get;

import java.util.UUID;

public interface ForGettingUserById {
    GetUserOutput getUserById(UUID id);
}
