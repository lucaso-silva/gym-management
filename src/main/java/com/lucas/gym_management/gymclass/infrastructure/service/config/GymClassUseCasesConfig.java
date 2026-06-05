package com.lucas.gym_management.gymclass.infrastructure.service.config;

import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassUseCase;
import com.lucas.gym_management.gymclass.application.ports.inbound.delete.DeleteGymClassUseCase;
import com.lucas.gym_management.gymclass.application.ports.inbound.get.GetGymClassByIdUseCase;
import com.lucas.gym_management.gymclass.application.ports.inbound.list.ListGymClassesUseCase;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymGateway;
import com.lucas.gym_management.gymclass.application.usecase.impl.CreateGymClassUseCaseImpl;
import com.lucas.gym_management.gymclass.application.usecase.impl.DeleteGymClassUseCaseImpl;
import com.lucas.gym_management.gymclass.application.usecase.impl.GetGymClassByIdUseCaseImpl;
import com.lucas.gym_management.gymclass.application.usecase.impl.ListGymClassesUseCaseImpl;
import com.lucas.gym_management.gymclass.application.usecase.validator.GymMemberValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GymClassUseCasesConfig {
    @Bean
    public CreateGymClassUseCase createGymClassUseCase(GymClassRepository gymClassRepository, GymGateway gymGateway, GymMemberValidator gymMemberValidator){
        return new CreateGymClassUseCaseImpl(gymClassRepository, gymGateway, gymMemberValidator);
    }

    @Bean
    public GetGymClassByIdUseCase getGymClassByIdUseCase(GymClassRepository gymClassRepository){
        return new GetGymClassByIdUseCaseImpl(gymClassRepository);
    }

    @Bean
    public ListGymClassesUseCase listGymClassesUseCase(GymClassRepository gymClassRepository){
        return new ListGymClassesUseCaseImpl(gymClassRepository);
    }

    @Bean
    public DeleteGymClassUseCase deleteGymClassUseCase(GymClassRepository gymClassRepository){
        return new DeleteGymClassUseCaseImpl(gymClassRepository);
    }

}
