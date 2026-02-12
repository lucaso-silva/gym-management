package com.lucas.gym_management.application.service;

import com.lucas.gym_management.application.domain.model.*;
import com.lucas.gym_management.application.exceptions.NotFoundException;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserInput;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserOutput;
import com.lucas.gym_management.application.ports.inbound.create.ForCreatingUser;
import com.lucas.gym_management.application.ports.inbound.delete.ForDeletingUserById;
import com.lucas.gym_management.application.ports.inbound.get.ForGettingUserById;
import com.lucas.gym_management.application.ports.inbound.get.ForGettingUserByLogin;
import com.lucas.gym_management.application.ports.inbound.get.GetUserOutput;
import com.lucas.gym_management.application.ports.inbound.update.ForUpdateUser;
import com.lucas.gym_management.application.ports.inbound.update.UpdateUserInput;
import com.lucas.gym_management.application.ports.inbound.update.UpdatedUserOutput;
import com.lucas.gym_management.application.ports.outbound.repository.UserRepository;
import jakarta.inject.Named;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Named
public class UserService implements ForCreatingUser,
        ForGettingUserById,
        ForGettingUserByLogin,
        ForUpdateUser,
        ForDeletingUserById {

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
                    userInput.birthDate(),
                    userInput.active());
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
        userRepository.deleteById(id);
    }

    @Override
    public GetUserOutput getUserById(UUID id) {
        Optional<User> userById = userRepository.findById(id);

        return userById.map(GetUserOutput::from)
                .orElseThrow(()-> new NotFoundException("User with id %s not found".formatted(id)));
    }

    @Override
    public GetUserOutput getUserByLogin(String login) {
        return null;
    }

    @Override
    public UpdatedUserOutput updateUser(UpdateUserInput userInput) {
        return null;
    }
}
