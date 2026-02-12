package com.lucas.gym_management.application.domain.model;

import lombok.Getter;

import java.util.Date;

@Getter
public class Student extends User {
    private Date birthDate;
    private boolean active;

    private Student(String name, String email, String login, String password, String phone, Address address, Date birthDate, boolean active) {
        super(name, email, login, password, phone, address);
        this.birthDate = birthDate;
        this.active = active;
    }

    public static Student newStudent(String name, String email, String login, String password, String phone, Address address, Date birthDate, boolean active) {
        //validations

        return new Student(name, email, login, password, phone, address, birthDate, active);
    }
}
