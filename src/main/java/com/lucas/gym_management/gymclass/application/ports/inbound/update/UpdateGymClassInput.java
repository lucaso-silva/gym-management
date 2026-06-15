package com.lucas.gym_management.gymclass.application.ports.inbound.update;

import com.lucas.gym_management.gymclass.application.dto.ScheduleDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateGymClassInput(@Size(min = 3)
                                  String name,
                                  UUID instructorId,
                                  @Min(5)
                                  Integer capacity,
                                  @Valid
                                  ScheduleDTO schedule) {
}
