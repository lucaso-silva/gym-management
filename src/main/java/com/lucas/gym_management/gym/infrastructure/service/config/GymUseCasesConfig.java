package com.lucas.gym_management.gym.infrastructure.service.config;

import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.delete.DeleteGymUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.get.GetGymByIdUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.list.ListGymsUseCase;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import com.lucas.gym_management.gym.application.usecase.impl.CreateGymUseCaseImpl;
import com.lucas.gym_management.gym.application.usecase.impl.DeleteGymUseCaseImpl;
import com.lucas.gym_management.gym.application.usecase.impl.GetGymByIdUseCaseImpl;
import com.lucas.gym_management.gym.application.usecase.impl.ListGymsUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GymUseCasesConfig {
    @Bean
    public CreateGymUseCase createGymUseCase(GymRepository gymRepository){
        return new CreateGymUseCaseImpl(gymRepository);
    }

    @Bean
    public GetGymByIdUseCase getGymByIdUseCase(GymRepository gymRepository){
        return new GetGymByIdUseCaseImpl(gymRepository);
    }

    @Bean
    public ListGymsUseCase listGymsUseCase(GymRepository gymRepository){
        return new ListGymsUseCaseImpl(gymRepository);
    }

    @Bean
    public DeleteGymUseCase deleteGymUseCase(GymRepository gymRepository){
        return new DeleteGymUseCaseImpl(gymRepository);
    }
}
