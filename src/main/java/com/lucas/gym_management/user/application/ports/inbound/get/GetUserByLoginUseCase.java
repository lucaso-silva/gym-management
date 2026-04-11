package com.lucas.gym_management.user.application.ports.inbound.get;

import com.lucas.gym_management.user.application.dto.user.UserOutput;

public interface GetUserByLoginUseCase {
    UserOutput getUserByLogin(String login);
}
