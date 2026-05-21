package com.lucas.gym_management.gymclass.application.ports.inbound.create;

import java.util.UUID;

public record CreateGymClassOutput(UUID id,
                                   String name,
                                   Integer capacity,
                                   ScheduleDTO scheduleDTO) {
}
