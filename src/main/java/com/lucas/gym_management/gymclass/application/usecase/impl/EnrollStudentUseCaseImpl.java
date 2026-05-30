package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.dto.GymClassOutput;
import com.lucas.gym_management.gymclass.application.exceptions.BusinessException;
import com.lucas.gym_management.gymclass.application.exceptions.NotFoundException;
import com.lucas.gym_management.gymclass.application.ports.inbound.manage_students.EnrollStudentUseCase;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class EnrollStudentUseCaseImpl implements EnrollStudentUseCase {
    private final GymClassRepository gymClassRepository;
    private final GymGateway gymGateway;

    @Override
    public GymClassOutput enrollStudent(UUID gymClassId, UUID studentId) {
        var gymClass = gymClassRepository.findById(gymClassId)
                .orElseThrow(()-> new NotFoundException("There is no gym class with id %s".formatted(gymClassId)));

        if(!gymGateway.isValidStudent(studentId)){
            throw new BusinessException("%s is not a valid student id".formatted(studentId));
        }

        gymClass.enrollStudent(studentId);

        return GymClassOutput.from(gymClassRepository.save(gymClass));
    }
}
