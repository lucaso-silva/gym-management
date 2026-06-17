package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.domain.model.valueobjects.Schedule;
import com.lucas.gym_management.gymclass.application.dto.GymClassOutput;
import com.lucas.gym_management.gymclass.application.exceptions.InvalidMemberException;
import com.lucas.gym_management.gymclass.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gymclass.application.exceptions.ScheduleConflictException;
import com.lucas.gym_management.gymclass.application.ports.inbound.update.UpdateGymClassInput;
import com.lucas.gym_management.gymclass.application.ports.inbound.update.UpdateGymClassUseCase;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.application.usecase.validator.GymMemberValidator;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UpdateGymClassUseCaseImpl implements UpdateGymClassUseCase {
    private final GymClassRepository gymClassRepository;
    private final GymMemberValidator gymMemberValidator;

    @Override
    public GymClassOutput updateGymClass(UUID gymClassId, UpdateGymClassInput input) {
        //TODO: validate user's request authorities

        var gymClass = gymClassRepository.findById(gymClassId)
                .orElseThrow(()-> new GymNotFoundException("There is no gym class with the id %s".formatted(gymClassId)));

        if(input.instructorId() != null){
            if(!gymMemberValidator.isInstructorFromGym(gymClass.getGymId(), input.instructorId())){
                throw new InvalidMemberException("%s is not a valid instructor id".formatted(input.instructorId()));
            }
            gymClass.assignInstructor(input.instructorId());
        }

        if(input.name() != null){
            gymClass.rename(input.name());
        }

        if(input.capacity() != null && input.capacity() > 0){
            gymClass.defineCapacity(input.capacity());
        }

        if(input.schedule() != null){
            if(gymClassRepository.hasScheduleConflict(gymClass.getGymId(),
                    gymClass.getId(),
                    input.schedule().dayOfWeek(),
                    input.schedule().room(),
                    input.schedule().startTime(),
                    input.schedule().endTime())){
                throw new ScheduleConflictException("There is already a class scheduled for this room and time");
            }

            gymClass.defineSchedule(new Schedule(input.schedule().dayOfWeek(),
                    input.schedule().room(),
                    input.schedule().startTime(),
                    input.schedule().endTime()));
        }

        return GymClassOutput.from(gymClassRepository.save(gymClass));
    }

}
