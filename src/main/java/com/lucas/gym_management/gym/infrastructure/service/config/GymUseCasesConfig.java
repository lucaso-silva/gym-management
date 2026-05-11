package com.lucas.gym_management.gym.infrastructure.service.config;

import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.delete.DeleteGymUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.get.GetGymByIdUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.list.ListGymsUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.manage_gym_classes.AddGymClassUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.manage_gym_classes.RemoveGymClassUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.manage_members.AddMemberUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.manage_members.RemoveMemberUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.update.UpdateGymUseCase;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import com.lucas.gym_management.gym.application.usecase.impl.*;
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
    public UpdateGymUseCase updateGymUseCase(GymRepository gymRepository){
        return new UpdateGymUseCaseImpl(gymRepository);
    }

    @Bean
    public DeleteGymUseCase deleteGymUseCase(GymRepository gymRepository){
        return new DeleteGymUseCaseImpl(gymRepository);
    }

    @Bean
    public AddMemberUseCase addMemberUseCase(GymRepository gymRepository){
        return new AddMemberUseCaseImpl(gymRepository);
    }

    @Bean
    public RemoveMemberUseCase removeMemberUseCase(GymRepository gymRepository){
        return new RemoveMemberUseCaseImpl(gymRepository);
    }

    @Bean
    public AddGymClassUseCase addGymClassUseCase(GymRepository gymRepository){
        return new AddGymClassUseCaseImpl(gymRepository);
    }

    @Bean
    public RemoveGymClassUseCase removeGymClassUseCase(GymRepository gymRepository){
        return new RemoveGymClassUseCaseImpl(gymRepository);
    }
}
