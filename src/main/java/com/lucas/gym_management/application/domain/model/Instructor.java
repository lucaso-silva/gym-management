package com.lucas.gym_management.application.domain.model;

import com.lucas.gym_management.application.domain.command.UpdateUserData;
import com.lucas.gym_management.application.domain.model.exceptions.DomainException;
import com.lucas.gym_management.application.domain.model.valueObjects.Address;
import lombok.Getter;

@Getter
public class Instructor extends User {
    private String cref;
    private String specialty;

    private Instructor(String name, String email, String login, String password, String phone, Address address, String cref, String specialty) {
        super(name, email, login, password, phone, address);
        fixCref(cref);
        updateSpecialty(specialty);
    }

    public static Instructor newInstructor(String name, String email, String login, String password, String phone, Address address, String cref, String specialty) {

        return new Instructor(name, email, login, password, phone, address, cref, specialty);
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
