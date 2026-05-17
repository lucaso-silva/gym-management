package com.lucas.gym_management.gymclass.application.domain.model;

import com.lucas.gym_management.gymclass.application.domain.model.exceptions.DomainException;
import com.lucas.gym_management.gymclass.application.domain.model.valueobjects.Schedule;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.*;

@Getter
public class GymClass {
    private final UUID id;
    private String name;
    private UUID instructorId;
    private Integer capacity;
    private final Set<UUID> enrolledStudents;
    private Schedule schedule;

    protected GymClass(String name, UUID instructorId, Integer capacity, Schedule schedule) {
        this.id = UUID.randomUUID();
        rename(name);
        assignInstructor(instructorId);
        defineCapacity(capacity);
        this.enrolledStudents = new HashSet<>();
        defineSchedule(schedule);
    }

    public static GymClass newGymClass(String name, UUID instructorId, Integer capacity, Schedule schedule) {
        return new GymClass(name,instructorId,capacity,schedule);
    }

    public void rename(String newName){
        if(newName == null || newName.trim().isEmpty()){
            throw new DomainException("Class name cannot be null or empty", "gym-class.domain-exception", HttpStatus.BAD_REQUEST);
        }
        this.name = newName;
    }

    public void assignInstructor(UUID instructorId){
        //TODO: check if instructor id is valid
        this.instructorId = instructorId;
    }

    public void defineCapacity(Integer capacity){
        if(capacity == null || capacity < 5){
            throw new DomainException("A class should allocate at least 5 students", "gym-class.domain-exception", HttpStatus.PRECONDITION_REQUIRED);
        }
        this.capacity = capacity;
    }

    public void enrollStudent(UUID studentId){
        //TODO: validate student id (is student? activeMembership?)
        if(this.enrolledStudents.contains(studentId)){
            throw new DomainException("Student is already enrolled in this class", "gym-class.domain-exception", HttpStatus.CONFLICT);
        }
        if(this.enrolledStudents.size() >= this.capacity){
            throw new DomainException("Class is at full capacity", "gym-class.domain-exception", HttpStatus.METHOD_NOT_ALLOWED);
        }
        this.enrolledStudents.add(studentId);
    }

    public void unenrollStudent(UUID studentId){
        if(!this.enrolledStudents.contains(studentId)){
            throw new DomainException("Student is not enrolled in this class", "gym-class.domain-exception", HttpStatus.NOT_FOUND);
        }
        this.enrolledStudents.remove(studentId);
    }

    public void defineSchedule(Schedule schedule){
        if(schedule == null){
            throw new DomainException("Schedule cannot be null", "gym-class.domain-exception", HttpStatus.BAD_REQUEST);
        }
        this.schedule = schedule;
    }
}
