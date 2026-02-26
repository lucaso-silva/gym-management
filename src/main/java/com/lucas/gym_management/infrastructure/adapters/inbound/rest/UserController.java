package com.lucas.gym_management.infrastructure.adapters.inbound.rest;

import com.lucas.gym_management.application.ports.inbound.create.ForCreatingUser;
import com.lucas.gym_management.application.ports.inbound.get.ForGettingUserById;
import com.lucas.gym_management.application.ports.inbound.list.ForListingUsers;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.request.CreateUserRequest;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.response.CreateUserResponse;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.response.ListUserResponse;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final ForCreatingUser createUserUseCase;
    private final ForListingUsers listUserUseCase;

    @GetMapping
    public ResponseEntity<List<ListUserResponse>> listUsers(@RequestParam(name="name", required = false) String filter){
        var usersList = listUserUseCase.listUsers(filter);
        return ResponseEntity.ok(
                usersList.stream()
                        .map(UserMapper::toUserListResponse)
                        .toList()
        );
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CreateUserResponse> create(@Valid @RequestBody CreateUserRequest newUSer) {

        var userOutput = createUserUseCase.createUser(UserMapper.requestToDTO(newUSer));
        URI uri = URI.create("/users/" + userOutput.id());
        var response = UserMapper.responseToDTO(userOutput);

        return ResponseEntity.created(uri).body(response);

    }
}
