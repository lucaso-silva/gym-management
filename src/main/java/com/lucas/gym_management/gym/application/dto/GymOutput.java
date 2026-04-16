package com.lucas.gym_management.gym.application.dto;

import com.lucas.gym_management.gym.application.domain.model.Gym;

import java.util.UUID;

public record GymOutput(UUID uuid,
                        String name,
                        String phone,
                        Integer members,
                        Integer activeClasses,
                        GymAddressDTO address) {
    public static GymOutput from(Gym gym) {
        return new GymOutput(gym.getId(),
                gym.getName(),
                gym.getPhone(),
                gym.getMembersIds().size(),
                gym.getGymClassesIds().size(),
                GymAddressDTO.from(gym.getAddress()));
    }
}
