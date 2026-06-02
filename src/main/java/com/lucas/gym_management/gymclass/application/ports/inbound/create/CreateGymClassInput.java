package com.lucas.gym_management.gymclass.application.ports.inbound.create;

import com.lucas.gym_management.gymclass.application.dto.ScheduleDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateGymClassInput(@NotBlank(message = "GymClass name cannot be empty")
                                  String name,
                                  @NotNull(message = "Gym class id cannot be null")
                                  UUID gymId,
                                  @NotNull(message = "Instructor ID cannot be null")
                                  UUID instructorId,
                                  @NotNull(message = "Capacity cannot be null")
                                  @Min(value = 5, message = "Capacity must be at least 5")
                                  Integer capacity,
                                  @Valid @NotNull(message = "Schedule cannot be null")
                                  ScheduleDTO schedule) {
}
