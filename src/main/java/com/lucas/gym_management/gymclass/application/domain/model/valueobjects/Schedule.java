package com.lucas.gym_management.gymclass.application.domain.model.valueobjects;

import com.lucas.gym_management.gymclass.application.domain.model.exceptions.InvalidTimeRangeException;
import com.lucas.gym_management.gymclass.application.domain.model.exceptions.RequiredFieldException;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record Schedule(DayOfWeek dayOfWeek,
                       String room,
                       LocalTime startTime,
                       LocalTime endTime) {
    public Schedule {
        if(dayOfWeek == null){
            throw new RequiredFieldException("Day of week is required");
        }

        if(room == null || room.isBlank()){
            throw new RequiredFieldException("Room is required");
        }

        if(startTime == null || endTime == null){
            throw new RequiredFieldException("Start time and end time are required");
        }

        if(!endTime.isAfter(startTime)){
            throw new InvalidTimeRangeException("End time must be after start time");
        }
    }
}
