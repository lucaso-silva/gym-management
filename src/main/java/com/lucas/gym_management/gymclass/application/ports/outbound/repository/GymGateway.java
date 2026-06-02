package com.lucas.gym_management.gymclass.application.ports.outbound.repository;

import java.util.UUID;

public interface GymGateway {
    boolean gymExists(UUID gymId);

    boolean isMember(UUID gymId, UUID memberId);
}
