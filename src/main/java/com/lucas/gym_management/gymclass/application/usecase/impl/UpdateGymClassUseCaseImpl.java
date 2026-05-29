package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.domain.model.valueobjects.Schedule;
import com.lucas.gym_management.gymclass.application.dto.GymClassOutput;
import com.lucas.gym_management.gymclass.application.exceptions.BusinessException;
import com.lucas.gym_management.gymclass.application.exceptions.NotFoundException;
import com.lucas.gym_management.gymclass.application.ports.inbound.update.UpdateGymClassInput;
import com.lucas.gym_management.gymclass.application.ports.inbound.update.UpdateGymClassUseCase;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UpdateGymClassUseCaseImpl implements UpdateGymClassUseCase {
    private final GymClassRepository gymClassRepository;
    private final GymGateway gymGateway;

    @Override
    public GymClassOutput updateGymClass(UUID gymClassId, UpdateGymClassInput input) {
        //TODO: validate user's request authorities

        var gymClass = gymClassRepository.findById(gymClassId)
                .orElseThrow(()-> new NotFoundException("There is no gym class with the id %s".formatted(gymClassId)));

        if(input.instructorId() != null){
            if(!gymGateway.isValidInstructor(input.instructorId())){
                throw new BusinessException("%s is not a valid instructor id".formatted(input.instructorId()));
            }
            gymClass.assignInstructor(input.instructorId());
        }

        if(input.name() != null){
            gymClass.rename(input.name());
        }

        if(input.capacity() != null){
            gymClass.defineCapacity(input.capacity());
        }

        if(input.schedule() != null){
            gymClass.defineSchedule(new Schedule(input.schedule().dayOfWeek(),
                    input.schedule().room(),
                    input.schedule().startTime(),
                    input.schedule().endTime()));
        }

        return GymClassOutput.from(gymClassRepository.save(gymClass));
    }
}
