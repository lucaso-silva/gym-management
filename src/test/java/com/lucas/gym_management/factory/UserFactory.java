package com.lucas.gym_management.factory;

import com.lucas.gym_management.application.domain.model.Manager;
import com.lucas.gym_management.application.domain.model.Student;
import com.lucas.gym_management.application.domain.model.UserType;
import com.lucas.gym_management.application.domain.model.valueObjects.Address;
import com.lucas.gym_management.application.dto.AddressDTO;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserInput;

import java.time.LocalDate;

public class UserFactory {
    public static CreateUserInput buildManagerInput() {
        return new CreateUserInput(UserType.MANAGER,
                "any-name",
                "test@email.com",
                "any-login",
                "pass-123",
                "any-phone-number",
                new AddressDTO("any-street",
                        "any-number",
                        "any-neighborhood",
                        "any-zip-code",
                        "any-city",
                        "any-state"),
                "any-gym-name",
                null,
                null,
                null);
    }

    public static CreateUserInput buildStudentInput(){
        LocalDate birthDate = LocalDate.now().minusYears(18);
        return new CreateUserInput(UserType.STUDENT,
                "any-name",
                "test@email.com",
                "any-login",
                "pass-123",
                "any-phone-number",
                new AddressDTO("any-street",
                        "any-number",
                        "any-neighborhood",
                        "any-zip-code",
                        "any-city",
                        "any-state"),
                null,
                null,
                null,
                birthDate);
    }

    public static Student buildStudent() {
        LocalDate birthDate = LocalDate.now().minusYears(18);
        return Student.newStudent("any-name",
                "test@email.com",
                "any-login",
                "pass-123",
                "any-phone-number",
                Address.newAddress("any-street",
                        "any-number",
                        "any-neighborhood",
                        "any-zip-code",
                        "any-city",
                        "any-state"),
                birthDate);
    }

    public static Manager buildManager() {
        return Manager.newManager("any-name",
                "test@email.com",
                "any-login",
                "pass-123",
                "any-phone-number",
                Address.newAddress("any-street",
                        "any-number",
                        "any-neighborhood",
                        "any-zip-code",
                        "any-city",
                        "any-state"),
                "any-gym-name");
    }
}
