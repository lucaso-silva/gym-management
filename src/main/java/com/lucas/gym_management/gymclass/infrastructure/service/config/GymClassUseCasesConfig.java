package com.lucas.gym_management.gymclass.infrastructure.service.config;

import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassUseCase;
import com.lucas.gym_management.gymclass.application.ports.inbound.delete.DeleteGymClassUseCase;
import com.lucas.gym_management.gymclass.application.ports.inbound.get.GetGymClassByIdUseCase;
import com.lucas.gym_management.gymclass.application.ports.inbound.list.ListGymClassesUseCase;
import com.lucas.gym_management.gymclass.application.ports.inbound.manage_students.EnrollStudentUseCase;
import com.lucas.gym_management.gymclass.application.ports.inbound.manage_students.UnenrollStudentUseCase;
import com.lucas.gym_management.gymclass.application.ports.inbound.update.UpdateGymClassUseCase;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymGateway;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.UserGateway;
import com.lucas.gym_management.gymclass.application.usecase.impl.*;
import com.lucas.gym_management.gymclass.application.usecase.validator.GymMemberValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GymClassUseCasesConfig {

    @Bean
    public GymMemberValidator gymMemberValidator(GymGateway gymGateway, UserGateway userGateway){
        return new GymMemberValidator(gymGateway, userGateway);
    }

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
    public EnrollStudentUseCase enrollStudentUseCase(GymClassRepository gymClassRepository, GymMemberValidator gymMemberValidator){
        return new EnrollStudentUseCaseImpl(gymClassRepository, gymMemberValidator);
    }

    @Bean
    public UnenrollStudentUseCase unenrollStudentUseCase(GymClassRepository gymClassRepository){
        return new UnenrollStudentUseCaseImpl(gymClassRepository);
    }

    @Bean
    public UpdateGymClassUseCase updateGymClassUseCase(GymClassRepository gymClassRepository, GymMemberValidator gymMemberValidator){
        return new UpdateGymClassUseCaseImpl(gymClassRepository, gymMemberValidator);
    }

    @Bean
    public DeleteGymClassUseCase deleteGymClassUseCase(GymClassRepository gymClassRepository){
        return new DeleteGymClassUseCaseImpl(gymClassRepository);
    }

}
