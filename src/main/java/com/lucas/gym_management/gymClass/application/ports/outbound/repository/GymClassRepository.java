package com.lucas.gym_management.gymclass.application.ports.outbound.repository;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;

public interface GymClassRepository {
    GymClass save(GymClass gymClass);
}
