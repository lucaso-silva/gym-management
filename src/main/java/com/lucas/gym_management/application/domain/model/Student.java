package com.lucas.gym_management.application.domain.model;

import com.lucas.gym_management.application.domain.command.UpdateUserData;
import lombok.Getter;

import java.util.Date;

@Getter
public class Student extends User {
    private Date birthDate;
    private boolean activeMembership;

    private Student(String name, String email, String login, String password, String phone, Address address, Date birthDate, boolean activeMembership) {
        super(name, email, login, password, phone, address);
        this.birthDate = birthDate;
        this.activeMembership = activeMembership;
    }

    public static Student newStudent(String name, String email, String login, String password, String phone, Address address, Date birthDate, boolean active) {
        //TODO: validations

        return new Student(name, email, login, password, phone, address, birthDate, active);
    }

    @Override
    public void applyUpdates(UpdateUserData data) {
        super.applyUpdates(data);

        if(data.birthDate() != null){
            //TODO: validations
            this.fixBirthDate(data.birthDate());
        }

        if(data.activeMembership() != null){
            if(data.activeMembership()){
                this.activateMembership();
            }else{
                this.deactivateMembership();
            }
        }
    }

    private void fixBirthDate(Date birthDate) {
        //TODO: validations
        this.birthDate = birthDate;
        updateInfo();
    }

    private void deactivateMembership() {
        this.activeMembership = false;
        updateInfo();
    }

    private void activateMembership() {
        this.activeMembership = true;
        updateInfo();
    }
}
