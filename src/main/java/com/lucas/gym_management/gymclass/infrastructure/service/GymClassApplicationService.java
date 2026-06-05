package com.lucas.gym_management.gymclass.infrastructure.service;

import com.lucas.gym_management.gymclass.application.dto.GymClassOutput;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassInput;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassOutput;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassUseCase;
import com.lucas.gym_management.gymclass.application.ports.inbound.delete.DeleteGymClassUseCase;
import com.lucas.gym_management.gymclass.application.ports.inbound.get.GetGymClassByIdUseCase;
import com.lucas.gym_management.gymclass.application.ports.inbound.list.ListGymClassOutput;
import com.lucas.gym_management.gymclass.application.ports.inbound.list.ListGymClassesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GymClassApplicationService {
    private final CreateGymClassUseCase createGymClassUseCase;
    private final GetGymClassByIdUseCase getGymClassByIdUseCase;
    private final ListGymClassesUseCase listGymClassesUseCase;
    private final DeleteGymClassUseCase deleteGymClassUseCase;

    @Transactional
    public CreateGymClassOutput createGymClass(CreateGymClassInput input){
        return createGymClassUseCase.createGymClass(input);
    }

    @Transactional(readOnly = true)
    public GymClassOutput getGymClassById(UUID id){
        return getGymClassByIdUseCase.getGymClassById(id);
    }

    @Transactional(readOnly = true)
    public List<ListGymClassOutput> listGymClasses(){
        return listGymClassesUseCase.listGymClasses();
    }

    @Transactional
    public void deleteGymClassById(UUID id){
        deleteGymClassUseCase.deleteGymClassById(id);
    }
}
