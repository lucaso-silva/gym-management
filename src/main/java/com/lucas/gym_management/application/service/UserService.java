package com.lucas.gym_management.application.service;

import com.lucas.gym_management.application.domain.command.UpdateUserData;
import com.lucas.gym_management.application.domain.model.valueObjects.Address;
import com.lucas.gym_management.application.exceptions.ConflictException;
import com.lucas.gym_management.application.exceptions.NotFoundException;
import com.lucas.gym_management.application.ports.inbound.update.ForUpdateUser;
import com.lucas.gym_management.application.ports.inbound.update.UpdateUserInput;
import com.lucas.gym_management.application.ports.inbound.update.UpdatedUserOutput;
import com.lucas.gym_management.application.ports.outbound.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService implements ForUpdateUser {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    @Override
    @Transactional
    public UpdatedUserOutput updateUser(UUID id, UpdateUserInput input) {
        var userById = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User with id %s not found".formatted(id)));

        if(input.email() != null &&
                userRepository.existsByEmailIdNot(input.email(),id)) {
            throw new ConflictException("Email %s already used.".formatted(input.email()));
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

        return UpdatedUserOutput.from(userRepository.updateUser(userById));
    }
}
