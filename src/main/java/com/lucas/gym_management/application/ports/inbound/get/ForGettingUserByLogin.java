package com.lucas.gym_management.application.ports.inbound.get;

public interface ForGettingUserByLogin {
    GetUserOutput getUserByLogin(String login);
}
