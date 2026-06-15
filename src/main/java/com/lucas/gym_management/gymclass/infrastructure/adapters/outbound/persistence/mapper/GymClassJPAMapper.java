package com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.mapper;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;
import com.lucas.gym_management.gymclass.application.domain.model.valueobjects.Schedule;
import com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.entities.GymClassJPAEntity;
import com.lucas.gym_management.gymclass.infrastructure.adapters.outbound.persistence.entities.ScheduleJPA;

import java.util.HashSet;

public class GymClassJPAMapper {

    public static GymClassJPAEntity toEntity(GymClass gymClass){
        if(gymClass == null){
            return null;
        }

        return GymClassJPAEntity.builder()
                .id(gymClass.getId())
                .name(gymClass.getName())
                .gymId(gymClass.getGymId())
                .instructorId(gymClass.getInstructorId())
                .capacity(gymClass.getCapacity())
                .enrolledStudents(new HashSet<>(gymClass.getEnrolledStudents()))
                .schedule(toEntity(gymClass.getSchedule()))
                .build();
    }

    public static GymClass toDomain(GymClassJPAEntity entity) {
        if(entity == null){
            return null;
        }

        return GymClass.restore(entity.getId(),
                entity.getName(),
                entity.getGymId(),
                entity.getInstructorId(),
                entity.getCapacity(),
                entity.getEnrolledStudents(),
                toDomain(entity.getSchedule()));
    }

    private static ScheduleJPA toEntity(Schedule schedule){
        return ScheduleJPA.builder()
                .dayOfWeek(schedule.dayOfWeek())
                .room(schedule.room())
                .startTime(schedule.startTime())
                .endTime(schedule.endTime())
                .build();
    }

    private static Schedule toDomain(ScheduleJPA schedule){
        return new Schedule(schedule.getDayOfWeek(),
                schedule.getRoom(),
                schedule.getStartTime(),
                schedule.getEndTime());
    }
}
