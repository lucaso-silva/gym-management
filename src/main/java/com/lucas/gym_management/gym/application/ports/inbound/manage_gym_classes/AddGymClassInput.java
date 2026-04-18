package com.lucas.gym_management.gym.application.ports.inbound.manage_gym_classes;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddGymClassInput(@NotNull UUID gymClassId) {
}
