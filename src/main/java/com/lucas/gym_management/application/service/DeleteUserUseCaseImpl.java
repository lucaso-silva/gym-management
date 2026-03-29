package com.lucas.gym_management.application.service;

import com.lucas.gym_management.application.domain.model.Manager;
import com.lucas.gym_management.application.domain.model.Student;
import com.lucas.gym_management.application.exceptions.ConflictException;
import com.lucas.gym_management.application.exceptions.NotAuthorizedException;
import com.lucas.gym_management.application.exceptions.NotFoundException;
import com.lucas.gym_management.application.ports.inbound.delete.DeleteUserByIdUseCase;
import com.lucas.gym_management.application.ports.outbound.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserByIdUseCase {
    private UserRepository userRepository;

    @Override
    @Transactional
    public void deleteUserById(UUID loggedInUserId, UUID id) {
        var loggedInUser = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new NotFoundException("User with id " + loggedInUserId + " not found"));

        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));

        if(!(loggedInUser instanceof Manager))
            throw new NotAuthorizedException("User cannot perform delete");

        if(user instanceof Student student && student.isActiveMembership())
            throw new ConflictException("Cannot delete a student with active membership, id %s".formatted(id));

        userRepository.deleteById(id);
    }
}
