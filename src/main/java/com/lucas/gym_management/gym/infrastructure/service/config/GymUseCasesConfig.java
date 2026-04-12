package com.lucas.gym_management.gym.infrastructure.service.config;

import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymUseCase;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import com.lucas.gym_management.gym.application.usecase.impl.CreateGymUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GymUseCasesConfig {
    @Bean
    public CreateGymUseCase createGymUseCase(GymRepository gymRepository){
        return new CreateGymUseCaseImpl(gymRepository);
    }
}
