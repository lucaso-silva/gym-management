package com.lucas.gym_management.user.application.service;

import com.lucas.gym_management.user.application.exceptions.NotFoundException;
import com.lucas.gym_management.user.application.ports.inbound.get.GetUserByIdUseCase;
import com.lucas.gym_management.user.application.ports.inbound.get.GetUserByLoginUseCase;
import com.lucas.gym_management.user.application.dto.user.UserOutput;
import com.lucas.gym_management.user.application.ports.outbound.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserByIdUseCase, GetUserByLoginUseCase {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserOutput getUserById(UUID id) {
        var userById = userRepository.findById(id);

        return userById.map(UserOutput::from)
                .orElseThrow(()-> new NotFoundException("User with id %s not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public UserOutput getUserByLogin(String login) {
        var userByLogin = userRepository.findByLogin(login);

        return userByLogin.map(UserOutput::from)
                .orElseThrow(()-> new NotFoundException("User with login %s not found".formatted(login)));
    }
}
