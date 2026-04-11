package com.lucas.gym_management.gym.application.ports.inbound.create;

import com.lucas.gym_management.gym.application.domain.model.Gym;

import java.util.UUID;

public record CreateGymOutput(UUID id,
                              String name,
                              String phone) {
    public static CreateGymOutput from(Gym gym) {
        return new CreateGymOutput(gym.getId(),
                gym.getName(),
                gym.getPhone());
    }
}
