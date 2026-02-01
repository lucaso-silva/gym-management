package com.lucas.gym_management.application.domain.model;

public class Instructor extends User {
    private String cref;
    private String specialty;

    private Instructor(String name, String email, String login, String password, String phone, Address address, String cref, String specialty) {
        super(name, email, login, password, phone, address);
        this.cref = cref;
        this.specialty = specialty;
    }

    public static Instructor newInstructor(String name, String email, String login, String password, String phone, Address address, String cref, String specialty) {
        //validations

        return new Instructor(name, email, login, password, phone, address, cref, specialty);
    }
}
