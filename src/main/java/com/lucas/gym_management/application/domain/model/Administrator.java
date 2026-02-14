package com.lucas.gym_management.application.domain.model;

import com.lucas.gym_management.application.domain.command.UpdateUserData;
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

    @Override
    public void applyUpdates(UpdateUserData data) {
        super.applyUpdates(data);

        if(data.gymName() != null && !data.gymName().isBlank()){
            this.updateGymName(data.gymName());
        }
    }

    private void updateGymName(String gymName) {
        this.gymName = gymName;
        updateInfo();
    }
}
