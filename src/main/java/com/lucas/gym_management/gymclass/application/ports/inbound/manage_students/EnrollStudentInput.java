package com.lucas.gym_management.gymclass.application.ports.inbound.manage_students;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnrollStudentInput(
        @NotNull(message = "Student Id cannot be null")
        UUID studentId
) {
}
