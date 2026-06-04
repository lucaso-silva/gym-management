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
    private final UUID gymId;
    private UUID instructorId;
    private Integer capacity;
    private final Set<UUID> enrolledStudents;
    private Schedule schedule;

    protected GymClass(String name, UUID gymId, UUID instructorId, Integer capacity, Schedule schedule) {
        if(gymId == null) {
            throw new DomainException("Gym id cannot be null");
        }

        this.id = UUID.randomUUID();
        rename(name);
        this.gymId = gymId;
        assignInstructor(instructorId);
        defineCapacity(capacity);
        this.enrolledStudents = new HashSet<>();
        defineSchedule(schedule);
    }

    protected GymClass(UUID id, String name, UUID gymId, UUID instructorId, Integer capacity, Set<UUID> enrolledStudents, Schedule schedule){
        this.id = id;
        this.name = name;
        this.gymId = gymId;
        this.instructorId = instructorId;
        this.capacity = capacity;
        this.enrolledStudents = new HashSet<>(enrolledStudents);
        this.schedule = schedule;
    };

    public static GymClass newGymClass(String name, UUID gymId, UUID instructorId, Integer capacity, Schedule schedule) {
        return new GymClass(name,gymId,instructorId,capacity,schedule);
    }

    public static GymClass restore(UUID id, String name, UUID gymId, UUID instructorId, Integer capacity, Set<UUID> enrolledStudents, Schedule schedule){
        return new GymClass(id, name, gymId, instructorId, capacity, enrolledStudents, schedule);
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
        if(studentId == null){
            throw new DomainException("Student ID cannot be null");
        }
        if(this.enrolledStudents.contains(studentId)){
            throw new DomainException("Student is already enrolled in this class");
        }
        if(this.enrolledStudents.size() >= this.capacity){
            throw new DomainException("Class is at full capacity");
        }
        this.enrolledStudents.add(studentId);
    }

    public void unenrollStudent(UUID studentId){
        if(studentId == null){
            throw new DomainException("Student ID cannot be null");
        }
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
