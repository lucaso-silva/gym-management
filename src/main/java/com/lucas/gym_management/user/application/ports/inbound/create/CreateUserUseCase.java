package com.lucas.gym_management.user.application.ports.inbound.create;

public interface CreateUserUseCase {
    CreateUserOutput create(CreateUserInput userInput);
}
