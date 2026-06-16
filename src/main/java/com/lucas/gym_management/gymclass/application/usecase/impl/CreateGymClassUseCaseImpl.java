package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;
import com.lucas.gym_management.gymclass.application.domain.model.valueobjects.Schedule;
import com.lucas.gym_management.gymclass.application.dto.ScheduleDTO;
import com.lucas.gym_management.gymclass.application.exceptions.InvalidMemberException;
import com.lucas.gym_management.gymclass.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gymclass.application.exceptions.ScheduleConflictException;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassInput;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassOutput;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassUseCase;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymGateway;
import com.lucas.gym_management.gymclass.application.usecase.validator.GymMemberValidator;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class CreateGymClassUseCaseImpl implements CreateGymClassUseCase {
    private final GymClassRepository gymClassRepository;
    private final GymGateway gymGateway;
    private final GymMemberValidator gymMemberValidator;

    @Override
    public CreateGymClassOutput createGymClass(CreateGymClassInput input) {
        if (!gymGateway.gymExists((input.gymId()))) {
            throw new GymNotFoundException("Gym not found with id %s".formatted(input
                    .gymId()));
        }

        if (!gymMemberValidator.isInstructorFromGym(input.gymId(), input.instructorId())) {
            throw new InvalidMemberException("Please provide a valid instructor id");
        }

        var avaliableSchedule = validateSchedule(input.gymId(), input.schedule());

        var newGymClass = GymClass.newGymClass(input.name(),
                input.gymId(),
                input.instructorId(),
                input.capacity(),
                avaliableSchedule);

        return CreateGymClassOutput.from(gymClassRepository.save(newGymClass));
    }

    private Schedule validateSchedule(UUID gymId, ScheduleDTO schedule) {

        if(gymClassRepository.hasScheduleConflict(
                gymId,
                schedule.dayOfWeek(),
                schedule.room(),
                schedule.startTime(),
                schedule.endTime())
        ) {
            throw new ScheduleConflictException("There is already a class scheduled for this room and time");
        };

        return new Schedule(schedule.dayOfWeek(),
                schedule.room(),
                schedule.startTime(),
                schedule.endTime());
    }

}
