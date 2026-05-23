package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;
import com.lucas.gym_management.gymclass.application.domain.model.valueobjects.Schedule;
import com.lucas.gym_management.gymclass.application.exceptions.InvalidInstructorIdException;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassInput;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassOutput;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassUseCase;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymGateway;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateGymClassUseCaseImpl implements CreateGymClassUseCase {
    private final GymClassRepository gymClassRepository;
    private final GymGateway gymGateway;

    @Override
    public CreateGymClassOutput createGymClass(CreateGymClassInput input) {

        if(!gymGateway.isValidInstructor(input.instructorId())){
            throw new InvalidInstructorIdException("Please provide a valid instructor id");
        }

        var schedule = new Schedule(input.schedule().dayOfWeek(),
                input.schedule().room(),
                input.schedule().startTime(),
                input.schedule().endTime());

        var newGymClass = GymClass.newGymClass(input.name(),
                input.instructorId(),
                input.capacity(),
                schedule);

        return CreateGymClassOutput.from(gymClassRepository.save(newGymClass));
    }
}
