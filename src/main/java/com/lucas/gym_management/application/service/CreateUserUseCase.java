package com.lucas.gym_management.application.service;

import com.lucas.gym_management.application.domain.command.CreateUserData;
import com.lucas.gym_management.application.domain.model.User;
import com.lucas.gym_management.application.exceptions.BusinessException;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserInput;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserOutput;
import com.lucas.gym_management.application.ports.inbound.create.ForCreatingUser;
import com.lucas.gym_management.application.ports.outbound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase implements ForCreatingUser {
    private UserRepository userRepository;

    @Override
    @Transactional
    public CreateUserOutput createUser(final CreateUserInput userInput) {

        if(userRepository.existsByEmail(userInput.email())){
            throw new BusinessException("Email %s already used.".formatted(userInput.email()));
        }

        if(userRepository.existsByLogin(userInput.login())){
            throw new BusinessException("Login %s already used.".formatted(userInput.login()));
        }

        var createUserData = CreateUserData.from(userInput);
        var user = userRepository.save(User.createNewUser(createUserData));

        return CreateUserOutput.from(user);
    }
}
