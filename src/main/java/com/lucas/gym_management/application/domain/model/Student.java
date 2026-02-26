package com.lucas.gym_management.application.domain.model;

import com.lucas.gym_management.application.domain.command.UpdateUserData;
import com.lucas.gym_management.application.domain.model.exceptions.DomainException;
import com.lucas.gym_management.application.domain.model.valueObjects.Address;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

@Getter
public class Student extends User {
    private LocalDate birthDate;
    private boolean activeMembership;

    private Student(String name, String email, String login, String password, String phone, Address address, LocalDate birthDate) {
        super(name, email, login, password, phone, address);
        fixBirthDate(birthDate);
        activateMembership();
    }

    private Student(UUID id, String name, String email, String login, String password, String phone, Address address, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDate birthDate, boolean activeMembership) {
        super(id, name, email, login, password, phone, address, createdAt, updatedAt);
        this.birthDate = birthDate;
        this.activeMembership = activeMembership;
    }

    public static Student newStudent(String name, String email, String login, String password, String phone, Address address, LocalDate birthDate) {

        return new Student(name, email, login, password, phone, address, birthDate);
    }

    public static Student restore(UUID id, String name, String email, String login, String password, String phone, Address address, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDate birthDate, boolean activeMembership) {
        return new Student(id, name, email, login, password, phone, address, createdAt, updatedAt, birthDate, activeMembership);
    }

    @Override
    public UserType getUserType() {
        return UserType.STUDENT;
    }

    @Override
    protected boolean applySpecificUpdates(UpdateUserData data) {
        boolean updated = false;

        if(data.birthDate() != null){
            this.fixBirthDate(data.birthDate());
            updated = true;
        }

        if(data.activeMembership() != null){
            boolean newState = data.activeMembership();

            if(this.activeMembership != newState){
                if(newState){
                    activateMembership();
                }else {
                    deactivateMembership();
                }
                updated = true;
            }
        }

        return updated;
    }

    private void fixBirthDate(LocalDate birthDate) {
        if(birthDate == null){
            throw new DomainException("Birth date cannot be empty");
        }

        var today = LocalDate.now();
        if(birthDate.isAfter(today)){
            throw new DomainException("Birth date cannot be in the future");
        }

        var age = Period.between(birthDate, today).getYears();
        if(age < 16){
            throw new DomainException("Student must be at least 16 years old");
        }

        this.birthDate = birthDate;
    }

    private void deactivateMembership() {
        if(!this.activeMembership) return;

        this.activeMembership = false;
    }

    private void activateMembership() {
        if(this.activeMembership) return;

        this.activeMembership = true;
    }
}
