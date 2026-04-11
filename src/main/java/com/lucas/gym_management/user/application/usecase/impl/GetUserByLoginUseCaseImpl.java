package com.lucas.gym_management.user.application.usecase.impl;

import com.lucas.gym_management.user.application.dto.user.UserOutput;
import com.lucas.gym_management.user.application.exceptions.NotFoundException;
import com.lucas.gym_management.user.application.ports.inbound.get.GetUserByLoginUseCase;
import com.lucas.gym_management.user.application.ports.outbound.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetUserByLoginUseCaseImpl implements GetUserByLoginUseCase {
    private final UserRepository userRepository;

    @Override
    public UserOutput getUserByLogin(String login) {
        var userByLogin = userRepository.findByLogin(login);

        return userByLogin.map(UserOutput::from)
                .orElseThrow(()-> new NotFoundException("User with login %s not found".formatted(login)));
    }
}
