package com.lucas.gym_management.infrastructure.adapters.inbound.rest.mapper;

import com.lucas.gym_management.application.domain.model.UserType;
import com.lucas.gym_management.application.dto.AddressDTO;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserInput;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserOutput;
import com.lucas.gym_management.application.ports.inbound.get.GetUserOutput;
import com.lucas.gym_management.application.ports.inbound.list.ListUserOutput;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.AddressRestDTO;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.request.CreateUserRequest;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.response.CreateUserResponse;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.response.ListUserResponse;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.response.UserResponse;

public class UserMapper {

    public static CreateUserInput requestToDTO(CreateUserRequest input) {
        var userType = toAppUserType(input);
        var address = toAddressDTO(input);

        return new CreateUserInput(userType,
                input.name(),
                input.email(),
                input.login(),
                input.password(),
                input.phone(),
                address,
                input.gymName(),
                input.cref(),
                input.specialty(),
                input.birthDate());
    }

    public static CreateUserResponse responseToDTO(CreateUserOutput output) {
        return new CreateUserResponse(output.id(),
                output.name(),
                output.login(),
                output.email());
    }

    public static ListUserResponse toUserListResponse(ListUserOutput output) {
        return new ListUserResponse(output.id(),
                output.name(),
                output.email());
    }

    public static UserResponse toUserResponse(GetUserOutput output) {
        var address = toAddressDTO(output);

        return new UserResponse(output.id(),
                output.name(),
                output.email(),
                output.login(),
                output.phone(),
                address);
    }

    private static UserType toAppUserType(CreateUserRequest input) {
        return UserType.valueOf(input.userType());
    }

    private static AddressDTO toAddressDTO(CreateUserRequest input) {
        return new AddressDTO(
                input.address().street(),
                input.address().number(),
                input.address().neighborhood(),
                input.address().zipCode(),
                input.address().city(),
                input.address().state()
        );
    }

    private static AddressRestDTO toAddressDTO(GetUserOutput output) {
        return new AddressRestDTO(output.address().street(),
                output.address().number(),
                output.address().neighborhood(),
                output.address().zipCode(),
                output.address().city(),
                output.address().state());
    }
}
