package com.lucas.gym_management.application.domain.model;

import com.lucas.gym_management.application.domain.command.UpdateUserData;
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
        //TODO: validations
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.lastUpdateDate = LocalDateTime.now();
    }

    private void renameTo(String newName){
        this.name = newName;
        updateInfo();
    }

    private void changeEmail(String newEmail){
        this.email = newEmail;
        updateInfo();
    }

    private void changeLogin(String newLogin){
        this.login = newLogin;
        updateInfo();
    }

    private void changePassword(String newPassword){
        this.password = newPassword;
        updateInfo();
    }

    private void changePhone(String newPhone){
        this.phone = newPhone;
        updateInfo();
    }

    private void updateAddress(Address newAddress){
        this.address = newAddress;
        updateInfo();
    }

    protected void updateInfo(){
        this.lastUpdateDate = LocalDateTime.now();
    }

    public void applyUpdates(UpdateUserData data){
        if(data.name() != null && !data.name().isBlank()){
            this.renameTo(data.name());
        }

        if(data.email() != null && !data.email().isBlank()){
            //TODO: check email uniqueness
            this.changeEmail(data.email());
        }

        if(data.phone() != null && !data.phone().isBlank()){
            this.changePhone(data.phone());
        }

        if(data.address() != null) {
            this.updateAddress(data.address());
        }
    }
}

