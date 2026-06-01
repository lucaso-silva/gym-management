package com.lucas.gym_management.user.application.domain.model;

import com.lucas.gym_management.user.application.domain.command.UpdateUserData;
import com.lucas.gym_management.user.application.domain.model.valueObjects.Address;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Manager extends User {

    private Manager(String name, String email, String login, String password, String phone, Address address) {
        super(name, email, login, password, phone, address);
    }

    private Manager(UUID id, String name, String email, String login, String password, String phone, Address address,LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, name, email, login, password, phone, address, createdAt, updatedAt);
    }

    public static Manager newManager(String name, String email, String login, String password, String phone, Address address) {

        return new Manager(name, email, login, password, phone, address);
    }

    public static Manager restore(UUID id, String name, String email, String login, String password, String phone, Address address, LocalDateTime createdAt, LocalDateTime updatedAt) {

        return new Manager(id, name, email, login, password, phone, address, createdAt, updatedAt);
    }

    @Override
    public UserType getUserType() {
        return UserType.MANAGER;
    }

    //FIXME: does it break SOLID? -> Liskov?
    @Override
    protected boolean applySpecificUpdates(UpdateUserData data) {
        return false;
    }
}
