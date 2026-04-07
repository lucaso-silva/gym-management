package com.lucas.gym_management.application.dto.user;

import com.lucas.gym_management.application.domain.model.UserType;
import com.lucas.gym_management.application.dto.AddressDTO;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@ToString
@Getter
public class StudentOutput extends UserOutput{
    private final LocalDate birthDate;
    private final boolean activeMembership;

    public StudentOutput(UUID id,
                         UserType userType,
                         String name,
                         String email,
                         String login,
                         String phone,
                         AddressDTO address,
                         LocalDate birthDate,
                         boolean activeMembership) {
        super(id, userType, name, email, login, phone, address);
        this.birthDate = birthDate;
        this.activeMembership = activeMembership;
    }
}

