package com.lucas.gym_management.application.dto.user;

import com.lucas.gym_management.application.domain.model.UserType;
import com.lucas.gym_management.application.dto.AddressDTO;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
public class ManagerOutput extends UserOutput{
    private final String gymName;

    public ManagerOutput(UUID id,
                         UserType userType,
                         String name,
                         String email,
                         String login,
                         String phone,
                         AddressDTO address,
                         String gymName){
        super(id,userType,name,email,login,phone,address);
        this.gymName = gymName;
    }
}
