package com.lucas.gym_management.gymclass.application.ports.inbound.list;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;
import com.lucas.gym_management.gymclass.application.dto.ScheduleDTO;

import java.util.UUID;

public record ListGymClassOutput(UUID id,
                                 String name,
                                 ScheduleDTO schedule) {
    public static ListGymClassOutput from(GymClass gymClass) {
        return new ListGymClassOutput(gymClass.getId(),
                gymClass.getName(),
                ScheduleDTO.from(gymClass.getSchedule()));
    }
}
