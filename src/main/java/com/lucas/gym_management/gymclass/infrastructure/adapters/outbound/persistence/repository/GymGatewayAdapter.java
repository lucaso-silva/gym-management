package com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.repository;

import com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence.repository.GymJPARepository;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class GymGatewayAdapter implements GymGateway {
    private final GymJPARepository gymJPARepository;

    @Override
    public boolean gymExists(UUID gymId) {
        return gymJPARepository.existsById(gymId);
    }

    @Override
    public boolean isMember(UUID gymId, UUID memberId) {
        return gymJPARepository.memberBelongsToGym(gymId, memberId);
    }
}
