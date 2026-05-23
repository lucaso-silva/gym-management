package com.lucas.gym_management.gymclass.application.ports.outbound.repository;

import java.util.UUID;

public interface GymGateway {
    boolean isValidInstructor(UUID id);
}
