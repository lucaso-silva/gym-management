package com.lucas.gym_management.application.domain.model;

import com.lucas.gym_management.application.domain.command.CreateUserData;
import com.lucas.gym_management.application.domain.command.UpdateUserData;
import com.lucas.gym_management.application.domain.model.exceptions.DomainException;
import com.lucas.gym_management.application.domain.model.valueObjects.Address;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
public abstract class User {
    private UUID id;
    private String name;
    private String email;
    private String login;
    @Getter(AccessLevel.NONE)
    private String password;
    private String phone;
    private Address address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected User(String name, String email, String login, String password, String phone, Address address) {
        this.id = UUID.randomUUID();
        renameTo(name);
        changeEmail(email);
        changeLogin(login);
        changePassword(password);
        updatePhone(phone);
        updateAddress(address);
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    protected User(UUID id, String name, String email, String login, String password, String phone, Address address, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User createNewUser(CreateUserData userInput) {

        User newUser = switch(userInput.userType()) {
            case STUDENT -> Student.newStudent(
                        userInput.name(),
                        userInput.email(),
                        userInput.login(),
                        userInput.password(),
                        userInput.phone(),
                        userInput.address(),
                        userInput.birthDate());
            case MANAGER -> Manager.newManager(
                        userInput.name(),
                        userInput.email(),
                        userInput.login(),
                        userInput.password(),
                        userInput.phone(),
                        userInput.address(),
                        userInput.gymName());
            case INSTRUCTOR -> Instructor.newInstructor(
                        userInput.name(),
                        userInput.email(),
                        userInput.login(),
                        userInput.password(),
                        userInput.phone(),
                        userInput.address(),
                        userInput.cref(),
                        userInput.specialty());
        };
        return newUser;
    }

    public static User restore(UUID id, UserType userType, String name, String email, String login, String password, String phone, Address address, LocalDateTime createdAt, LocalDateTime updatedAt, Map<String, Object> extraFields) {

        return switch(userType){
            case STUDENT -> Student.restore(id,
                    name,
                    email,
                    login,
                    password,
                    phone,
                    address,
                    createdAt,
                    updatedAt,
                    (LocalDate) extraFields.get("birthDate"),
                    (Boolean) extraFields.get("activeMembership"));

            case INSTRUCTOR -> Instructor.restore(id,
                    name,
                    email,
                    login,
                    password,
                    phone,
                    address,
                    createdAt,
                    updatedAt,
                    (String) extraFields.get("cref"),
                    (String) extraFields.get("specialty"));

            case MANAGER -> Manager.restore(id,
                    name,
                    email,
                    login,
                    password,
                    phone,
                    address,
                    createdAt,
                    updatedAt,
                    (String) extraFields.get("gymName"));

        };
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
        if(!newEmail.matches("^(.+)@(\\S+)$")) {
            throw new DomainException("Invalid email address");
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
        this.updatedAt = LocalDateTime.now();
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

