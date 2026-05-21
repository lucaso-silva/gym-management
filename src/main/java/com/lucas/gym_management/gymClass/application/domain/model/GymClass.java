package com.lucas.gym_management.gymclass.application.domain.model;

import com.lucas.gym_management.gymclass.application.domain.model.exceptions.DomainException;
import com.lucas.gym_management.gymclass.application.domain.model.valueobjects.Schedule;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
            throw new DomainException("Class name cannot be null or empty");
        }
        this.name = newName;
    }

    public void assignInstructor(UUID instructorId){
        if(instructorId == null){
            throw new DomainException("Instructor ID cannot be null");
        }
        //TODO: check if instructor id is valid
        this.instructorId = instructorId;
    }

    public void defineCapacity(Integer capacity){
        if(capacity == null || capacity < 5){
            throw new DomainException("A class should allocate at least 5 students");
        }
        if(enrolledStudents != null && capacity < enrolledStudents.size()){
            throw new DomainException("Capacity cannot be lower than enrolled students");
        }
        this.capacity = capacity;
    }

    public void enrollStudent(UUID studentId){
        //TODO: validate student id (is student? activeMembership?)
        if(this.enrolledStudents.contains(studentId)){
            throw new DomainException("Student is already enrolled in this class");
        }
        if(this.enrolledStudents.size() >= this.capacity){
            throw new DomainException("Class is at full capacity");
        }
        this.enrolledStudents.add(studentId);
    }

    public void unenrollStudent(UUID studentId){
        if(!this.enrolledStudents.contains(studentId)){
            throw new DomainException("Student is not enrolled in this class");
        }
        this.enrolledStudents.remove(studentId);
    }

    public void defineSchedule(Schedule schedule){
        if(schedule == null){
            throw new DomainException("Schedule cannot be null");
        }
        this.schedule = schedule;
    }
}
