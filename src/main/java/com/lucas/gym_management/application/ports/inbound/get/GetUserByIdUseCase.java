package com.lucas.gym_management.application.ports.inbound.get;

import java.util.UUID;

public interface GetUserByIdUseCase {
    GetUserOutput getUserById(UUID id);
}
