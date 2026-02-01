package com.lucas.gym_management.application.ports.inbound.create;

public interface ForCreatingUser {
    CreateUserOutput createUser(CreateUserInput userInput);
}
