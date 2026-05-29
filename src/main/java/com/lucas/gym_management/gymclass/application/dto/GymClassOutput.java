package com.lucas.gym_management.gymclass.application.dto;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;

import java.util.UUID;

public record GymClassOutput(UUID id,
                             String name,
                             Integer capacity,
                             Integer numEnrolledStudents,
                             ScheduleDTO schedule) {
    public static GymClassOutput from(GymClass gymClass) {
        return new GymClassOutput(
                gymClass.getId(),
                gymClass.getName(),
                gymClass.getCapacity(),
                gymClass.getEnrolledStudents().size(),
                new ScheduleDTO(gymClass.getSchedule().dayOfWeek(),
                        gymClass.getSchedule().room(),
                        gymClass.getSchedule().startTime(),
                        gymClass.getSchedule().endTime())
        );
    }
}
