package com.lucas.gym_management.application.service;

import com.lucas.gym_management.application.domain.command.CreateUserData;
import com.lucas.gym_management.application.domain.model.User;
import com.lucas.gym_management.application.exceptions.ConflictException;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserInput;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserOutput;
import com.lucas.gym_management.application.ports.outbound.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements com.lucas.gym_management.application.ports.inbound.create.CreateUserUseCase {
    private UserRepository userRepository;

    @Override
    @Transactional
    public CreateUserOutput createUser(final CreateUserInput userInput) {

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
