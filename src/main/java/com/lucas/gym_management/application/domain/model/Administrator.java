package com.lucas.gym_management.application.domain.model;

import com.lucas.gym_management.application.domain.command.UpdateUserData;
import com.lucas.gym_management.application.domain.model.exceptions.DomainException;
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
        updateGymName(gymName);
    }

    public static Administrator newAdministrator(String name, String email, String login, String password, String phone, Address address, String gymName) {

        return new Administrator(name, email, login, password, phone, address, gymName);
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
