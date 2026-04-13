package com.lucas.gym_management.gym.application.dto;

import com.lucas.gym_management.gym.application.domain.model.Gym;

import java.util.UUID;

public record GymOutput(UUID uuid,
                        String name,
                        String phone,
                        GymAddressDTO address) {
    public static GymOutput from(Gym gym) {
        return new GymOutput(gym.getId(),
                gym.getName(),
                gym.getPhone(),
                GymAddressDTO.from(gym.getAddress()));
    }
}
