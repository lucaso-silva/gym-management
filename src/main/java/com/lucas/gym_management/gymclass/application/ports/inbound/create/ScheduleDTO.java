package com.lucas.gym_management.gymclass.application.ports.inbound.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record ScheduleDTO(@NotNull(message = "You must provide a day of week")
                          DayOfWeek dayOfWeek,
                          @NotBlank(message = "You must provide a room")
                          String room,
                          @NotNull(message = "You must provide a start time")
                          LocalTime startTime,
                          @NotNull(message = "You must provide an end time")
                          LocalTime endTime) {
}
