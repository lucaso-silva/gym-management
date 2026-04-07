package com.lucas.gym_management.user.infrastructure.adapters.inbound.rest.mapper;

import com.lucas.gym_management.user.application.domain.model.UserType;
import com.lucas.gym_management.user.application.dto.AddressDTO;
import com.lucas.gym_management.user.application.ports.inbound.create.CreateUserInput;
import com.lucas.gym_management.user.application.ports.inbound.create.CreateUserOutput;
import com.lucas.gym_management.user.application.ports.inbound.list.ListUserOutput;
import com.lucas.gym_management.user.application.ports.inbound.update.UpdateUserInput;
import com.lucas.gym_management.user.infrastructure.adapters.inbound.rest.dtos.request.CreateUserRequest;
import com.lucas.gym_management.user.infrastructure.adapters.inbound.rest.dtos.request.UpdateUserRequest;
import com.lucas.gym_management.user.infrastructure.adapters.inbound.rest.dtos.response.CreateUserResponse;
import com.lucas.gym_management.user.infrastructure.adapters.inbound.rest.dtos.response.ListUserResponse;

public class UserMapper {
    private UserMapper() {}

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
                output.email(),
                output.userType().name());
    }

    public static ListUserResponse toUserListResponse(ListUserOutput output) {
        return new ListUserResponse(output.id(),
                output.name(),
                output.email(),
                output.userType().name());
    }

    public static UpdateUserInput toUpdateUserInput(UpdateUserRequest request) {
        AddressDTO address = request.address() == null ? null : toAddressDTO(request);

        return new UpdateUserInput(request.name(),
                request.email(),
                request.phone(),
                address,
                request.gymName(),
                request.cref(),
                request.specialty(),
                request.birthDate(),
                request.activeMembership());
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

    private static AddressDTO toAddressDTO(UpdateUserRequest input) {
        return new AddressDTO(
                input.address().street(),
                input.address().number(),
                input.address().neighborhood(),
                input.address().zipCode(),
                input.address().city(),
                input.address().state()
        );
    }
}
