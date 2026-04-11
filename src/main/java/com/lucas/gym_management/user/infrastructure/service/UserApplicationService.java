package com.lucas.gym_management.user.infrastructure.service;

import com.lucas.gym_management.user.application.dto.user.UserOutput;
import com.lucas.gym_management.user.application.ports.inbound.create.CreateUserInput;
import com.lucas.gym_management.user.application.ports.inbound.create.CreateUserOutput;
import com.lucas.gym_management.user.application.ports.inbound.create.CreateUserUseCase;
import com.lucas.gym_management.user.application.ports.inbound.delete.DeleteUserUseCase;
import com.lucas.gym_management.user.application.ports.inbound.get.GetUserByIdUseCase;
import com.lucas.gym_management.user.application.ports.inbound.get.GetUserByLoginUseCase;
import com.lucas.gym_management.user.application.ports.inbound.list.ListUserOutput;
import com.lucas.gym_management.user.application.ports.inbound.list.ListUsersUseCase;
import com.lucas.gym_management.user.application.ports.inbound.update.UpdateUserInput;
import com.lucas.gym_management.user.application.ports.inbound.update.UpdateUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserApplicationService {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final GetUserByLoginUseCase getUserByLoginUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @Transactional
    public CreateUserOutput createUser(CreateUserInput userInput) {
        return createUserUseCase.create(userInput);
    }

    @Transactional(readOnly = true)
    public UserOutput getUserById(UUID id){
        return getUserByIdUseCase.getUserById(id);
    }

    @Transactional(readOnly = true)
    public UserOutput getUserByLogin(String login){
        return getUserByLoginUseCase.getUserByLogin(login);
    }

    @Transactional(readOnly = true)
    public List<ListUserOutput> listUsers(String name){
        return listUsersUseCase.listUsers(name);
    }

    @Transactional
    public UserOutput updateUser(UUID loggedInUserId, UUID userId, UpdateUserInput input){
        return updateUserUseCase.updateUser(loggedInUserId, userId, input);
    }

    @Transactional
    public void deleteUserById(UUID loggedInUserId, UUID id) {
        deleteUserUseCase.deleteUserById(loggedInUserId, id);
    }

}
