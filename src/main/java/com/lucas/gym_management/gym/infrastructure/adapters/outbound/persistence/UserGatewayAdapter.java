package com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence;

import com.lucas.gym_management.gym.application.ports.outbound.repository.UserGateway;
import com.lucas.gym_management.user.infrastructure.adapters.outbound.persistence.repository.UserJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.lucas.gym_management.user.infrastructure.adapters.outbound.persistence.entities.UserTypeJPA.MANAGER;
import static com.lucas.gym_management.user.infrastructure.adapters.outbound.persistence.entities.UserTypeJPA.STUDENT;

@Component("gymUserGatewayAdapter")
@AllArgsConstructor
public class UserGatewayAdapter implements UserGateway {
    private final UserJPARepository userRepository;

    @Override
    public boolean managerExists(UUID managerId) {
        return userRepository.existsByIdAndUserType(managerId,
                MANAGER);
    }

    @Override
    public boolean userExists(UUID uuid) {
        return userRepository.existsById(uuid);
    }

    @Override
    public boolean studentHasActiveMembership(UUID memberId) {
        return userRepository.existsByIdAndUserTypeAndActiveMembership(memberId, STUDENT, true);
    }
}
