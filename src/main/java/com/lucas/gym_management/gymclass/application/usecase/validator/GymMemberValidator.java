package com.lucas.gym_management.gymclass.application.usecase.validator;

import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymGateway;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.UserGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class GymMemberValidator {
    private final GymGateway gymGateway;
    private final UserGateway userGateway;

    public boolean isInstructorFromGym(UUID gymId, UUID instructorId){
        return gymGateway.isMember(gymId, instructorId) && userGateway.isValidInstructor(instructorId);
    }

    public boolean isActiveStudentFromGym(UUID gymId, UUID studentId) {
        return gymGateway.isMember(gymId, studentId) && userGateway.isActiveStudent(studentId);
    }
}
