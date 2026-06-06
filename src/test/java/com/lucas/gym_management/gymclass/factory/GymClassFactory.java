package com.lucas.gym_management.gymclass.factory;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;
import com.lucas.gym_management.gymclass.application.domain.model.valueobjects.Schedule;
import com.lucas.gym_management.gymclass.application.ports.inbound.create.CreateGymClassInput;
import com.lucas.gym_management.gymclass.application.dto.ScheduleDTO;
import com.lucas.gym_management.gymclass.application.ports.inbound.update.UpdateGymClassInput;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class GymClassFactory {
        private static final UUID instructorId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        private static final UUID gymId = UUID.fromString("11111111-1111-1111-1111-111111111111");

    public static CreateGymClassInput buildCreateGymClassInput(){

        return new CreateGymClassInput(
                "Any-gym-class-name",
                gymId,
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
                gymId,
                instructorId,
                5,
                new Schedule(scheduleDTO.dayOfWeek(),
                        scheduleDTO.room(),
                        scheduleDTO.startTime(),
                        scheduleDTO.endTime()));
    }

    public static List<GymClass> buildListOfGymClasses() {
        var gymClass1 = GymClass.newGymClass("First-gym-class-name",
                gymId,
                instructorId,
                10,
                new Schedule(DayOfWeek.MONDAY,
                        "First-gym-class-room",
                        LocalTime.of(10, 0),
                        LocalTime.of(10,30)));

        var gymClass2 = GymClass.newGymClass("Second-gym-class-name",
                gymId,
                instructorId,
                20,
                new Schedule(DayOfWeek.TUESDAY,
                        "Second-gym-class-room",
                        LocalTime.of(11, 0),
                        LocalTime.of(11,30)));

        return List.of(gymClass1, gymClass2);
    }

    public static UpdateGymClassInput buildUpdateGymClassInput(UUID newInstructorId) {
        return new UpdateGymClassInput("Updated-gym-class-name",
                newInstructorId,
                10,
                new ScheduleDTO(DayOfWeek.TUESDAY,
                        "Updated-gym-class-room",
                        LocalTime.of(12, 0),
                        LocalTime.of(12,30)));
    }
}


