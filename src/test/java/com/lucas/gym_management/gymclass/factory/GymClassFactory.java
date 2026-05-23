package com.lucas.gym_management.gymclass.factory;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;
import com.lucas.gym_management.gymclass.application.domain.model.valueobjects.Schedule;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassInput;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.ScheduleDTO;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

public class GymClassFactory {
        private static final UUID instructorId = UUID.randomUUID();

    public static CreateGymClassInput buildCreateGymClassInput(){

        return new CreateGymClassInput(
                "Any-class-name",
                instructorId,
                5,
                buildScheduleDTO()
        );
    }

    private static ScheduleDTO buildScheduleDTO(){
        return new ScheduleDTO(
                DayOfWeek.MONDAY,
                "Any gym room",
                LocalTime.of(10, 0),
                LocalTime.of(10,30)
        );
    }

    public static GymClass buildGymClass() {
        var scheduleDTO = buildScheduleDTO();

        return GymClass.newGymClass("Any-class-name",
                instructorId,
                5,
                new Schedule(scheduleDTO.dayOfWeek(),
                        scheduleDTO.room(),
                        scheduleDTO.startTime(),
                        scheduleDTO.endTime()));
    }
}


