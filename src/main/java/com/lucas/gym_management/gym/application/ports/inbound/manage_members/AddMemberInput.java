package com.lucas.gym_management.gym.application.ports.inbound.manage_members;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddMemberInput(@NotNull UUID memberId) {
}
