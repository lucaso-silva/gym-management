package com.lucas.gym_management.gymclass.application.ports.inbound.create;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;

import java.util.UUID;

public record CreateGymClassOutput(UUID id,
                                   String name,
                                   UUID instructorId,
                                   Integer capacity,
                                   ScheduleDTO schedule) {
    public static CreateGymClassOutput from(GymClass gymClass) {
        return new CreateGymClassOutput(gymClass.getId(),
                gymClass.getName(),
                gymClass.getInstructorId(),
                gymClass.getCapacity(),
                new ScheduleDTO(gymClass.getSchedule().dayOfWeek(),
                        gymClass.getSchedule().room(),
                        gymClass.getSchedule().startTime(),
                        gymClass.getSchedule().endTime())
        );
    }
}
