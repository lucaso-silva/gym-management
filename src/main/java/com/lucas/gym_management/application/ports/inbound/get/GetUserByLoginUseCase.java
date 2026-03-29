package com.lucas.gym_management.application.ports.inbound.get;

public interface GetUserByLoginUseCase {
    GetUserOutput getUserByLogin(String login);
}
