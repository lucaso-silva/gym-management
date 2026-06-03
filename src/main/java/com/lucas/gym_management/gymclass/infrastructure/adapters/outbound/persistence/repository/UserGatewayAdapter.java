package com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.repository;

import com.lucas.gym_management.gymclass.application.ports.outbound.repository.UserGateway;
import com.lucas.gym_management.user.infrastructure.adapters.outbound.persistence.entities.UserTypeJPA;
import com.lucas.gym_management.user.infrastructure.adapters.outbound.persistence.repository.UserJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("gymClassUserGatewayAdapter")
@AllArgsConstructor
public class UserGatewayAdapter implements UserGateway {
    private final UserJPARepository userJPARepository;

    @Override
    public boolean isValidInstructor(UUID id) {
        return userJPARepository.existsByIdAndUserType(id, UserTypeJPA.INSTRUCTOR);
    }

    @Override
    public boolean isActiveStudent(UUID id) {
        return userJPARepository.existsByIdAndUserTypeAndActiveMembership(id, UserTypeJPA.STUDENT, true);
    }
}
