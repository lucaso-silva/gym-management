package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.exceptions.ActionNotAllowedException;
import com.lucas.gym_management.gymclass.application.exceptions.BusinessException;
import com.lucas.gym_management.gymclass.application.exceptions.NotFoundException;
import com.lucas.gym_management.gymclass.application.ports.inbound.delete.DeleteGymClassUseCase;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeleteGymClassUseCaseImpl implements DeleteGymClassUseCase {
    private final GymClassRepository gymClassRepository;

    @Override
    public void deleteGymClassById(UUID id) {
        //TODO: validate if user has authority to delete a gymClass (jwt token)
        var gymClass = gymClassRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no gym class with id %s".formatted(id)));

        if(!gymClass.getEnrolledStudents().isEmpty()){
            throw new ActionNotAllowedException("You cannot delete a gym class with enrolled students");
        }

        gymClassRepository.deleteById(gymClass.getId());
    }
}
