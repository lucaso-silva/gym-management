package com.lucas.gym_management.user.application.ports.inbound.get;

import com.lucas.gym_management.user.application.dto.user.UserOutput;

import java.util.UUID;

public interface GetUserByIdUseCase {
    UserOutput getUserById(UUID id);
}
