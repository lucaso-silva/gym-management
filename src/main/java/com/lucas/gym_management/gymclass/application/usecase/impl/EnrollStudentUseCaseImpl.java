package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.dto.GymClassOutput;
import com.lucas.gym_management.gymclass.application.exceptions.BusinessException;
import com.lucas.gym_management.gymclass.application.exceptions.NotFoundException;
import com.lucas.gym_management.gymclass.application.ports.inbound.manage_students.EnrollStudentInput;
import com.lucas.gym_management.gymclass.application.ports.inbound.manage_students.EnrollStudentUseCase;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.application.usecase.validator.GymMemberValidator;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class EnrollStudentUseCaseImpl implements EnrollStudentUseCase {
    private final GymClassRepository gymClassRepository;
    private final GymMemberValidator gymMemberValidator;

    @Override
    public GymClassOutput enrollStudent(UUID gymClassId, EnrollStudentInput input) {
        var gymClass = gymClassRepository.findById(gymClassId)
                .orElseThrow(()-> new NotFoundException("There is no gym class with id %s".formatted(gymClassId)));

        if(!gymMemberValidator.isActiveStudentFromGym(gymClass.getGymId(), input.studentId())){
            throw new BusinessException("Only active students can be enrolled in a class");
        }

        gymClass.enrollStudent(input.studentId());

        return GymClassOutput.from(gymClassRepository.save(gymClass));
    }
}
