package com.lucas.gym_management.application.ports.inbound.get;

import com.lucas.gym_management.application.domain.model.User;
import com.lucas.gym_management.application.dto.AddressDTO;

import java.util.UUID;

public record GetUserOutput(
        UUID id,
        String name,
        String email,
        String login,
        String phone,
        AddressDTO address
) {
    public static GetUserOutput from(User user) {
        AddressDTO addressDTO = new AddressDTO(
                user.getAddress().getStreet(),
                user.getAddress().getNumber(),
                user.getAddress().getNeighborhood(),
                user.getAddress().getZipCode(),
                user.getAddress().getCity(),
                user.getAddress().getState());

        return new GetUserOutput(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getPhone(),
                addressDTO
                );
    }
}
