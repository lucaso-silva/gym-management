package com.lucas.gym_management.application.dto.user;

import com.lucas.gym_management.application.domain.model.UserType;
import com.lucas.gym_management.application.dto.AddressDTO;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
public class InstructorOutput extends UserOutput{
    private final String cref;
    private final String specialty;

    public InstructorOutput(UUID id,
                            UserType userType,
                            String name,
                            String email,
                            String login,
                            String phone,
                            AddressDTO address,
                            String cref,
                            String specialty){
        super(id,userType,name,email,login,phone,address);
        this.cref = cref;
        this.specialty = specialty;
    }
}
