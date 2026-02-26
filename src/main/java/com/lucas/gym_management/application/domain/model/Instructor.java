package com.lucas.gym_management.application.domain.model;

import com.lucas.gym_management.application.domain.command.UpdateUserData;
import com.lucas.gym_management.application.domain.model.exceptions.DomainException;
import com.lucas.gym_management.application.domain.model.valueObjects.Address;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Instructor extends User {
    private String cref;
    private String specialty;

    private Instructor(String name, String email, String login, String password, String phone, Address address, String cref, String specialty) {
        super(name, email, login, password, phone, address);
        fixCref(cref);
        updateSpecialty(specialty);
    }

    private Instructor(UUID id, String name, String email, String login, String password, String phone, Address address, LocalDateTime createdAt, LocalDateTime updatedAt, String cref, String specialty) {
        super(id, name, email, login, password, phone, address, createdAt, updatedAt);
        this.cref = cref;
        this.specialty = specialty;
    }

    public static Instructor newInstructor(String name, String email, String login, String password, String phone, Address address, String cref, String specialty) {

        return new Instructor(name, email, login, password, phone, address, cref, specialty);
    }

    public static Instructor restore(UUID id, String name, String email, String login, String password, String phone, Address address, LocalDateTime createdAt, LocalDateTime updatedAt, String cref, String specialty) {

        return new Instructor(id, name, email, login, password,  phone, address, createdAt, updatedAt, cref, specialty);
    }

    @Override
    public UserType getUserType() {
        return UserType.INSTRUCTOR;
    }

    @Override
    protected boolean applySpecificUpdates(UpdateUserData data){
        boolean updated = false;

        if(data.cref() != null){
            this.fixCref(data.cref());
            updated = true;
        }

        if(data.specialty() != null){
            this.updateSpecialty(data.specialty());
            updated = true;
        }

        return updated;
    }

    private void fixCref(String cref) {
        if(cref == null || cref.isBlank()){
            throw new DomainException("Cref cannot be empty");
        }
        this.cref = cref;
    }

    private void updateSpecialty(String specialty) {
        if(specialty == null || specialty.isBlank()){
            throw new DomainException("Specialty cannot be empty");
        }
        this.specialty = specialty;
    }
}
