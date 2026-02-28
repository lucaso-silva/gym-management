package com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.request;

import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.AddressRestDTO;

import java.time.LocalDate;

public record CreateUserRequest(String userType,
                                String name,
                                String email,
                                String login,
                                String password,
                                String phone,
                                AddressRestDTO address,
                                String gymName,
                                String cref,
                                String specialty,
                                LocalDate birthDate) {
}
