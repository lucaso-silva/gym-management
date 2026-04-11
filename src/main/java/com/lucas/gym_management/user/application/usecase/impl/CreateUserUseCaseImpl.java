package com.lucas.gym_management.user.application.usecase.impl;

import com.lucas.gym_management.user.application.domain.command.CreateUserData;
import com.lucas.gym_management.user.application.domain.model.User;
import com.lucas.gym_management.user.application.exceptions.ConflictException;
import com.lucas.gym_management.user.application.ports.inbound.create.CreateUserInput;
import com.lucas.gym_management.user.application.ports.inbound.create.CreateUserOutput;
import com.lucas.gym_management.user.application.ports.inbound.create.CreateUserUseCase;
import com.lucas.gym_management.user.application.ports.outbound.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;

    @Override
    public CreateUserOutput create(final CreateUserInput userInput) {

        if(userRepository.existsByEmail(userInput.email())){
            throw new ConflictException("Email %s already used.".formatted(userInput.email()));
        }

        if(userRepository.existsByLogin(userInput.login())){
            throw new ConflictException("Login %s already used.".formatted(userInput.login()));
        }

        var createUserData = CreateUserData.from(userInput);
        var user = userRepository.save(User.createNewUser(createUserData));

        return CreateUserOutput.from(user);
    }
}
