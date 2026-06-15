package com.lucas.gym_management.gymclass.application.ports.inbound.manage_students;

import com.lucas.gym_management.gymclass.application.dto.GymClassOutput;

import java.util.UUID;

public interface EnrollStudentUseCase {
    GymClassOutput enrollStudent(UUID gymClassId, EnrollStudentInput studentId);
}
