package com.lucas.gym_management.application.ports.inbound.get;

import com.lucas.gym_management.application.dto.user.UserOutput;

public interface GetUserByLoginUseCase {
    UserOutput getUserByLogin(String login);
}
