package com.lucas.gym_management.application.domain.model;

import com.lucas.gym_management.application.domain.command.UpdateUserData;
import com.lucas.gym_management.application.domain.model.exceptions.DomainException;
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
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdateDate;

    protected User(String name, String email, String login, String password, String phone, Address address) {
        renameTo(name);
        changeEmail(email);
        changeLogin(login);
        changePassword(password);
        updatePhone(phone);
        updateAddress(address);
        this.createdDate = LocalDateTime.now();
        this.lastUpdateDate = null;
    }

    private void renameTo(String newName){
        if(newName == null || newName.isBlank()) {
            throw new DomainException("Name cannot be empty");
        }
        this.name = newName;
    }

    private void changeEmail(String newEmail){
        if(newEmail == null || newEmail.isBlank()) {
            throw new DomainException("Email cannot be empty");
        }
        this.email = newEmail;
    }

    private void changeLogin(String newLogin){
        if(newLogin == null || newLogin.isBlank()) {
            throw new DomainException("Login cannot be empty");
        }
        this.login = newLogin;
    }

    private void changePassword(String newPassword){
        if(newPassword == null || newPassword.isBlank()) {
            throw new DomainException("Password cannot be empty");
        }
        this.password = newPassword;
    }

    private void updatePhone(String newPhone){
        if(newPhone == null || newPhone.isBlank()) {
            throw new DomainException("Phone cannot be empty");
        }
        this.phone = newPhone;
    }

    private void updateAddress(Address newAddress){
        if(newAddress == null) {
            throw new DomainException("Address cannot be empty");
        }
        this.address = newAddress;
    }

    public final void applyUpdates(UpdateUserData data){
        boolean updated = applyBaseUpdates(data);
        updated |= applySpecificUpdates(data);

        if(updated) updateInfo();
    }

    protected void updateInfo(){
        this.lastUpdateDate = LocalDateTime.now();
    }

    protected boolean applyBaseUpdates(UpdateUserData data){
        boolean updated = false;

        if(data.name() != null){
            this.renameTo(data.name());
            updated = true;
        }

        if(data.email() != null){
            this.changeEmail(data.email());
            updated = true;
        }

        if(data.phone() != null){
            this.updatePhone(data.phone());
            updated = true;
        }

        if(data.address() != null) {
            this.updateAddress(data.address());
            updated = true;
        }

        return updated;
    }

    protected abstract boolean applySpecificUpdates(UpdateUserData data);
}

