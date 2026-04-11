package com.lucas.gym_management.user.infrastructure.service.config;

import com.lucas.gym_management.user.application.ports.inbound.create.CreateUserUseCase;
import com.lucas.gym_management.user.application.ports.inbound.delete.DeleteUserUseCase;
import com.lucas.gym_management.user.application.ports.inbound.get.GetUserByIdUseCase;
import com.lucas.gym_management.user.application.ports.inbound.get.GetUserByLoginUseCase;
import com.lucas.gym_management.user.application.ports.inbound.list.ListUsersUseCase;
import com.lucas.gym_management.user.application.ports.inbound.update.UpdateUserUseCase;
import com.lucas.gym_management.user.application.ports.outbound.repository.UserRepository;
import com.lucas.gym_management.user.application.usecase.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCasesConfig {
    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository userRepository) {
        return new CreateUserUseCaseImpl(userRepository);
    }

    @Bean
    public GetUserByIdUseCase getUserByIdUseCase(UserRepository userRepository) {
        return new GetUserByIdUseCaseImpl(userRepository);
    }

    @Bean
    public GetUserByLoginUseCase getUserByLoginUseCase(UserRepository userRepository) {
        return new GetUserByLoginUseCaseImpl(userRepository);
    }

    @Bean
    public ListUsersUseCase listUsersUseCase(UserRepository userRepository) {
        return new ListUsersUseCaseImpl(userRepository);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserRepository userRepository){
        return new UpdateUserUseCaseImpl(userRepository);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserRepository userRepository){
        return new DeleteUserUseCaseImpl(userRepository);
    }
}
