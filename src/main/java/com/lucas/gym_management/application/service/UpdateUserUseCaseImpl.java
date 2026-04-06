package com.lucas.gym_management.application.service;

import com.lucas.gym_management.application.domain.command.UpdateUserData;
import com.lucas.gym_management.application.domain.model.Manager;
import com.lucas.gym_management.application.domain.model.valueObjects.Address;
import com.lucas.gym_management.application.dto.user.UserOutput;
import com.lucas.gym_management.application.exceptions.ConflictException;
import com.lucas.gym_management.application.exceptions.NotAuthorizedException;
import com.lucas.gym_management.application.exceptions.NotFoundException;
import com.lucas.gym_management.application.ports.inbound.update.UpdateUserInput;
import com.lucas.gym_management.application.ports.inbound.update.UpdateUserUseCase;
import com.lucas.gym_management.application.ports.outbound.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserOutput updateUser(UUID loggedInUserId, UUID userId, UpdateUserInput input) {

        if(!loggedInUserId.equals(userId)) {
            var loggedInUser = userRepository.findById(loggedInUserId)
                    .orElseThrow(()-> new NotFoundException("User with id %s not found".formatted(loggedInUserId)));

            if(!(loggedInUser instanceof Manager))
                throw new NotAuthorizedException("Logged in user is not allowed to perform update");
        }

        var userById = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("User with id %s not found".formatted(userId)));

        if(input.email() != null &&
                userRepository.existsByEmailIdNot(input.email(),userId)) {
            throw new ConflictException("Email %s already used".formatted(input.email()));
        }

        Address address = input.address() == null ? null :
                Address.newAddress(input.address().street(),
                    input.address().number(),
                    input.address().neighborhood(),
                    input.address().zipCode(),
                    input.address().city(),
                    input.address().state());

        var updateData = new UpdateUserData(input.name(),
                input.email(),
                input.phone(),
                address,
                input.gymName(),
                input.cref(),
                input.specialty(),
                input.birthDate(),
                input.activeMembership());

        userById.applyUpdates(updateData);

        return UserOutput.from(userRepository.updateUser(userById));
    }
}
