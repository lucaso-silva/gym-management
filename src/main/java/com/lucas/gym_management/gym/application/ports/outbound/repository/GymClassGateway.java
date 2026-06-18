package com.lucas.gym_management.gym.application.ports.outbound.repository;

import java.util.UUID;

public interface GymClassGateway {

    boolean instructorHasAssignedClasses(UUID gymId, UUID instructorId);
}
