package com.lucas.gym_management.application.ports.inbound.create;

public interface CreateUserUseCase {
    CreateUserOutput createUser(CreateUserInput userInput);
}
