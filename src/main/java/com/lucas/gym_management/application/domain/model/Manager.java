package com.lucas.gym_management.application.domain.model;

import com.lucas.gym_management.application.domain.command.UpdateUserData;
import com.lucas.gym_management.application.domain.model.exceptions.DomainException;
import com.lucas.gym_management.application.domain.model.valueObjects.Address;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Manager extends User {
    private String gymName;

    private Manager(String name, String email, String login, String password, String phone, Address address, String gymName) {
        super(name, email, login, password, phone, address);
        updateGymName(gymName);
    }

    private Manager(UUID id, String name, String email, String login, String password, String phone, Address address,LocalDateTime createdAt, LocalDateTime updatedAt, String gymName) {
        super(id, name, email, login, password, phone, address, createdAt, updatedAt);
        this.gymName = gymName;
    }

    public static Manager newManager(String name, String email, String login, String password, String phone, Address address, String gymName) {

        return new Manager(name, email, login, password, phone, address, gymName);
    }

    public static Manager restore(UUID id, String name, String email, String login, String password, String phone, Address address, LocalDateTime createdAt, LocalDateTime updatedAt, String gymName) {

        return new Manager(id, name, email, login, password, phone, address, createdAt, updatedAt, gymName);
    }

    @Override
    public UserType getUserType() {
        return UserType.MANAGER;
    }

    @Override
    protected boolean applySpecificUpdates(UpdateUserData data) {
        if(data.gymName() != null){
            this.updateGymName(data.gymName());
            return true;
        }
        return false;
    }

    private void updateGymName(String gymName) {
        if(gymName == null || gymName.isBlank()){
            throw new DomainException("Gym name cannot be empty");
        }
        this.gymName = gymName;
    }
}
