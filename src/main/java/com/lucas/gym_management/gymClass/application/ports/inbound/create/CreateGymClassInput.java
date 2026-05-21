package com.lucas.gym_management.gymclass.application.ports.inbound.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateGymClassInput(@NotBlank(message = "GymClass name cannot be empty")
                                  String name,
                                  @NotNull(message = "Instructor ID cannot be null")
                                  UUID instructorId,
                                  @Min(value = 5, message = "Capacity must be at least 5")
                                  Integer capacity,
                                  @NotNull(message = "Schedule cannot be null")
                                  ScheduleDTO scheduleDTO) {
}
