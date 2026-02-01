package com.lucas.gym_management.application.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class User {
    private UUID id;
    private String name;
    private String email;
    private String login;
    private String password;
    private String phone;
    private Address address;
    private LocalDateTime lastUpdateDate;

    protected User(String name, String email, String login, String password, String phone, Address address) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.address = address;
        lastUpdateDate = LocalDateTime.now();
    }
}

