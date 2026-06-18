package com.lucas.gym_management.gym.application.ports.outbound.repository;

import java.util.UUID;

public interface UserGateway {
    boolean managerExists(UUID managerId);

    boolean userExists(UUID uuid);

    boolean studentHasActiveMembership(UUID memberId);
}
