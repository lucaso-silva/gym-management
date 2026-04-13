package com.lucas.gym_management.gym.application.ports.inbound.list;

import com.lucas.gym_management.gym.application.domain.model.Gym;

import java.util.UUID;

public record ListGymOutput(UUID id,
                            String name,
                            String phone) {
    public static ListGymOutput from(Gym gym) {
        return new ListGymOutput(gym.getId(),
                gym.getName(),
                gym.getPhone());
    }
}
