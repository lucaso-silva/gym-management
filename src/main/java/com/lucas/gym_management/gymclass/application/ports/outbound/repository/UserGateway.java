package com.lucas.gym_management.gymclass.application.ports.outbound.repository;

import java.util.UUID;

public interface UserGateway {
    boolean isValidInstructor(UUID id);

    boolean isActiveStudent(UUID id);
}
