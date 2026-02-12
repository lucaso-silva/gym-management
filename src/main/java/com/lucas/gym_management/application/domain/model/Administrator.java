package com.lucas.gym_management.application.domain.model;

import lombok.Getter;

@Getter
public class Administrator extends User {
    private String gymName;

    private Administrator(String name,
                          String email,
                          String login,
                          String password,
                          String phone,
                          Address address,
                          String gymName) {
        super(name, email, login, password, phone, address);
        this.gymName = gymName;
    }

    public static Administrator newAdministrator(String name, String email, String login, String password, String phone, Address address, String gymName) {
        //validations

        return new Administrator(name, email, login, password, phone, address, gymName);
    }
}
