package com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence;

import com.lucas.gym_management.gym.application.ports.outbound.repository.GymClassGateway;
import com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.repository.GymClassJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("gymGymClassGatewayAdapter")
@AllArgsConstructor
public class GymClassGatewayAdapter implements GymClassGateway {
    private final GymClassJPARepository gymClassRepository;

    @Override
    public boolean instructorHasAssignedClasses(UUID gymId, UUID instructorId) {
        return gymClassRepository.existsByGymIdAndInstructorId(gymId, instructorId);
    }
}
