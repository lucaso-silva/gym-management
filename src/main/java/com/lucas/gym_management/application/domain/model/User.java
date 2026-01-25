package com.lucas.gym_management.application.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class User {
    private UUID id;
    private String name;
    private String email;
    private String login;
    private String password;
    private String phone;
    private Address address;
    private LocalDateTime lastUpdateDate;
}

