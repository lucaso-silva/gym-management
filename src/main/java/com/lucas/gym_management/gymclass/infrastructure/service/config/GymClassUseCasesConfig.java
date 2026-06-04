package com.lucas.gym_management.gymclass.infrastructure.service.config;

import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassUseCase;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymGateway;
import com.lucas.gym_management.gymclass.application.usecase.impl.CreateGymClassUseCaseImpl;
import com.lucas.gym_management.gymclass.application.usecase.validator.GymMemberValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GymClassUseCasesConfig {
    @Bean
    public CreateGymClassUseCase createGymClassUseCase(GymClassRepository gymClassRepository, GymGateway gymGateway, GymMemberValidator gymMemberValidator){
        return new CreateGymClassUseCaseImpl(gymClassRepository, gymGateway, gymMemberValidator);
    }

}
