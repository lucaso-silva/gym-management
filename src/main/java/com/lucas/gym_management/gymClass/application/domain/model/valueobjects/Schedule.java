package com.lucas.gym_management.gymclass.application.domain.model.valueobjects;

import com.lucas.gym_management.gymclass.application.domain.model.exceptions.DomainException;
import org.springframework.http.HttpStatus;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record Schedule(DayOfWeek dayOfWeek,
                       String room,
                       LocalTime startTime,
                       LocalTime endTime) {
    public Schedule {
        if(dayOfWeek == null){
            throw new DomainException("Day of week is required", "gym-class.domain-exception", HttpStatus.BAD_REQUEST);
        }

        if(room == null || room.isBlank()){
            throw new DomainException("Room is required", "gym-class.domain-exception", HttpStatus.BAD_REQUEST);
        }

        if(startTime == null || endTime == null){
            throw new DomainException("Start time and end time are required", "gym-class.domain-exception", HttpStatus.BAD_REQUEST);
        }

        if(!endTime.isAfter(startTime)){
            throw new DomainException("End time must be after start time", "gym-class.domain-exception", HttpStatus.BAD_REQUEST);
        }
    }
}
