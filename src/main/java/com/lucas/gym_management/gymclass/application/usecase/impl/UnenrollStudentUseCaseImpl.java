package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.dto.GymClassOutput;
import com.lucas.gym_management.gymclass.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gymclass.application.ports.inbound.manage_students.UnenrollStudentUseCase;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UnenrollStudentUseCaseImpl implements UnenrollStudentUseCase {
    private final GymClassRepository gymClassRepository;

    @Override
    public GymClassOutput unenrollStudent(UUID gymClassId, UUID studentId) {
        var gymClass = gymClassRepository.findById(gymClassId)
                .orElseThrow(()-> new GymNotFoundException("There is no gym class with id %s".formatted(gymClassId)));

        gymClass.unenrollStudent(studentId);

        return GymClassOutput.from(gymClassRepository.save(gymClass));
    }
}
