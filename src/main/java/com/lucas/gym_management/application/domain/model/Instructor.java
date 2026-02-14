package com.lucas.gym_management.application.domain.model;

import com.lucas.gym_management.application.domain.command.UpdateUserData;
import lombok.Getter;

@Getter
public class Instructor extends User {
    private String cref;
    private String specialty;

    private Instructor(String name, String email, String login, String password, String phone, Address address, String cref, String specialty) {
        super(name, email, login, password, phone, address);
        this.cref = cref;
        this.specialty = specialty;
    }

    public static Instructor newInstructor(String name, String email, String login, String password, String phone, Address address, String cref, String specialty) {
        //TODO: validations

        return new Instructor(name, email, login, password, phone, address, cref, specialty);
    }

    @Override
    public void applyUpdates(UpdateUserData data){
        super.applyUpdates(data);

        if(data.cref() != null && !data.cref().isBlank()){
            this.fixCref(data.cref());
        }

        if(data.specialty() != null && !data.specialty().isBlank()){
            this.updateSpecialty(data.specialty());
        }
    }

    private void fixCref(String cref) {
        this.cref = cref;
        updateInfo();
    }

    private void updateSpecialty(String specialty) {
        this.specialty = specialty;
        updateInfo();
    }
}
