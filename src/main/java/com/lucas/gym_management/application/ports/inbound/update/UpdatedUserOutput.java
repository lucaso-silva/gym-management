package com.lucas.gym_management.application.ports.inbound.update;

import com.lucas.gym_management.application.domain.model.User;
import com.lucas.gym_management.application.dto.AddressDTO;

import java.util.UUID;

public record UpdatedUserOutput(
        UUID id,
        String name,
        String login,
        String email,
        String phone,
        AddressDTO address
) {
    public static UpdatedUserOutput from(User user) {
        AddressDTO addressDTO = new AddressDTO(user.getAddress().getStreet(),
                user.getAddress().getNumber(),
                user.getAddress().getNeighborhood(),
                user.getAddress().getZipCode(),
                user.getAddress().getCity(),
                user.getAddress().getState());

        return new UpdatedUserOutput(
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getPhone(),
                addressDTO);
    }
}
