package com.lucas.gym_management.user.application.usecase.impl;

import com.lucas.gym_management.user.application.dto.user.UserOutput;
import com.lucas.gym_management.user.application.exceptions.NotFoundException;
import com.lucas.gym_management.user.application.ports.inbound.get.GetUserByIdUseCase;
import com.lucas.gym_management.user.application.ports.outbound.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {
    private final UserRepository userRepository;

    @Override
    public UserOutput getUserById(UUID id) {
        var userById = userRepository.findById(id);

        return userById.map(UserOutput::from)
                .orElseThrow(()-> new NotFoundException("User with id %s not found".formatted(id)));
    }

}
