package com.lucas.gym_management.application.domain.command;

import com.lucas.gym_management.application.domain.model.UserType;
import com.lucas.gym_management.application.domain.model.valueObjects.Address;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserInput;

import java.time.LocalDate;

public record CreateUserData(UserType userType,
                             String name,
                             String email,
                             String login,
                             String password,
                             String phone,
                             Address address,
                             String gymName,
                             String cref,
                             String specialty,
                             LocalDate birthDate
                             ) {
    public static CreateUserData from(CreateUserInput input) {
        var address = Address.newAddress(input.address().street(),
                input.address().number(),
                input.address().neighborhood(),
                input.address().zipCode(),
                input.address().city(),
                input.address().state());

        return new CreateUserData(input.userType(),
                input.name(),
                input.email(),
                input.login(),
                input.password(),
                input.phone(),
                address,
                input.gymName(),
                input.cref(),
                input.specialty(),
                input.birthDate()
                );
    }
}
