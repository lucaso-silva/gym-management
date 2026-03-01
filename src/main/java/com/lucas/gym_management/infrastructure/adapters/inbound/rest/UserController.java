package com.lucas.gym_management.infrastructure.adapters.inbound.rest;

import com.lucas.gym_management.application.ports.inbound.create.ForCreatingUser;
import com.lucas.gym_management.application.ports.inbound.delete.ForDeletingUserById;
import com.lucas.gym_management.application.ports.inbound.get.ForGettingUserById;
import com.lucas.gym_management.application.ports.inbound.list.ForListingUsers;
import com.lucas.gym_management.application.ports.inbound.update.ForUpdateUser;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.request.CreateUserRequest;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.request.UpdateUserRequest;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.response.CreateUserResponse;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.response.ListUserResponse;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.dtos.response.UserResponse;
import com.lucas.gym_management.infrastructure.adapters.inbound.rest.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final ForCreatingUser createUserUseCase;
    private final ForListingUsers listUsersUseCase;
    private final ForGettingUserById getUserByIdUseCase;
    private final ForDeletingUserById deleteUserUseCase;
    private final ForUpdateUser updateUserUseCase;

    @GetMapping
    public ResponseEntity<List<ListUserResponse>> listUsers(@RequestParam(name="name", required = false) String filter){
        var usersList = listUsersUseCase.listUsers(filter);
        return ResponseEntity.ok(
                usersList.stream()
                        .map(UserMapper::toUserListResponse)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable UUID id){
        var userById = getUserByIdUseCase.getUserById(id);

        return ResponseEntity.ok(UserMapper.toUserResponse(userById));
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> create(@Valid @RequestBody CreateUserRequest newUSer) {

        var userOutput = createUserUseCase.createUser(UserMapper.requestToDTO(newUSer));
        URI uri = URI.create("/users/" + userOutput.id());
        var response = UserMapper.responseToDTO(userOutput);

        return ResponseEntity.created(uri).body(response);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id,
                                                   @RequestBody UpdateUserRequest input){
        var updatedUser = updateUserUseCase.updateUser(id, UserMapper.toUpdateUserInput(input));

        return ResponseEntity.ok(UserMapper.toUserResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {

        deleteUserUseCase.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
