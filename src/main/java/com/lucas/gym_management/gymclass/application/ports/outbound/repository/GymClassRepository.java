package com.lucas.gym_management.gymclass.application.ports.outbound.repository;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GymClassRepository {
    GymClass save(GymClass gymClass);

    Optional<GymClass> findById(UUID id);

    List<GymClass> findAll();

    void deleteById(UUID gymClassId);

    boolean hasScheduleConflict(UUID gymId, DayOfWeek dayOfWeek, String room, LocalTime startTime, LocalTime endTime);

    boolean hasScheduleConflict(UUID gymId, UUID gymClassId, DayOfWeek dayOfWeek, String room, LocalTime startTime, LocalTime endTime);
}
