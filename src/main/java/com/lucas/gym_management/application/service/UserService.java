package com.lucas.gym_management.application.service;

import com.lucas.gym_management.application.domain.command.UpdateUserData;
import com.lucas.gym_management.application.domain.model.*;
import com.lucas.gym_management.application.exceptions.NotFoundException;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserInput;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserOutput;
import com.lucas.gym_management.application.ports.inbound.create.ForCreatingUser;
import com.lucas.gym_management.application.ports.inbound.delete.ForDeletingUserById;
import com.lucas.gym_management.application.ports.inbound.get.ForGettingUserById;
import com.lucas.gym_management.application.ports.inbound.get.ForGettingUserByLogin;
import com.lucas.gym_management.application.ports.inbound.get.GetUserOutput;
import com.lucas.gym_management.application.ports.inbound.list.ForListingUsers;
import com.lucas.gym_management.application.ports.inbound.list.ListUserOutput;
import com.lucas.gym_management.application.ports.inbound.update.ForUpdateUser;
import com.lucas.gym_management.application.ports.inbound.update.UpdateUserInput;
import com.lucas.gym_management.application.ports.inbound.update.UpdatedUserOutput;
import com.lucas.gym_management.application.ports.outbound.repository.UserRepository;
import jakarta.inject.Named;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Named
public class UserService implements ForCreatingUser,
        ForGettingUserById,
        ForGettingUserByLogin,
        ForListingUsers,
        ForDeletingUserById,
        ForUpdateUser {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    @Override
    public CreateUserOutput createUser(final CreateUserInput userInput) {

        Address userAddress = Address.newAddress(userInput.address().street(),
                userInput.address().number(),
                userInput.address().neighborhood(),
                userInput.address().zipCode(),
                userInput.address().city(),
                userInput.address().state());

        User newUser = switch(userInput.userType()){
            case STUDENT -> Student.newStudent(
                    userInput.name(),
                    userInput.email(),
                    userInput.login(),
                    userInput.password(),
                    userInput.phone(),
                    userAddress,
                    userInput.birthDate());
            case ADMINISTRATOR -> Administrator.newAdministrator(
                    userInput.name(),
                    userInput.email(),
                    userInput.login(),
                    userInput.password(),
                    userInput.phone(),
                    userAddress,
                    userInput.gymName());
            case INSTRUCTOR -> Instructor.newInstructor(
                    userInput.name(),
                    userInput.email(),
                    userInput.login(),
                    userInput.password(),
                    userInput.phone(),
                    userAddress,
                    userInput.cref(),
                    userInput.specialty());
        };

        var user = userRepository.create(newUser);
        return CreateUserOutput.from(user);
    }

    @Override
    public void deleteUserById(UUID id) {
        userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User with id " + id + " not found")
        );
        userRepository.deleteById(id);
    }

    @Override
    public GetUserOutput getUserById(UUID id) {
        var userById = userRepository.findById(id);

        return userById.map(GetUserOutput::from)
                .orElseThrow(()-> new NotFoundException("User with id %s not found".formatted(id)));
    }

    @Override
    public GetUserOutput getUserByLogin(String login) {
        var userByLogin = userRepository.findByLogin(login);

        return userByLogin.map(GetUserOutput::from)
                .orElseThrow(()-> new NotFoundException("User with login %s not found".formatted(login)));
    }

    @Override
    public List<ListUserOutput> listUsers(String name) {
        List<User> userList = name == null || name.isBlank()
                ? userRepository.findAll()
                : userRepository.findByNameLike(name);

        return userList.stream().map(ListUserOutput::from).toList();
    }

    @Override
    public UpdatedUserOutput updateUser(UpdateUserInput input) {
        var userById = userRepository.findById(input.id())
                .orElseThrow(()-> new NotFoundException("User with id %s not found".formatted(input.id())));

        Address address = null;

        if(input.address() != null){
            address = Address.newAddress(input.address().street(),
                    input.address().number(),
                    input.address().neighborhood(),
                    input.address().zipCode(),
                    input.address().city(),
                    input.address().state());
        }

        var updateData = new UpdateUserData(input.name(),
                input.email(),
                input.phone(),
                address,
                input.gymName(),
                input.cref(),
                input.specialty(),
                input.birthDate(),
                input.activeMembership());

        userById.applyUpdates(updateData);

        return UpdatedUserOutput.from(userRepository.updateUser(userById));
    }
}
