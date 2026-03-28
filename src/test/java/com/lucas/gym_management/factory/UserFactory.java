package com.lucas.gym_management.factory;

import com.lucas.gym_management.application.domain.model.Instructor;
import com.lucas.gym_management.application.domain.model.Manager;
import com.lucas.gym_management.application.domain.model.Student;
import com.lucas.gym_management.application.domain.model.UserType;
import com.lucas.gym_management.application.domain.model.valueObjects.Address;
import com.lucas.gym_management.application.dto.AddressDTO;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserInput;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserFactory {

    public static CreateUserInput buildInstructorInput() {
        return new CreateUserInput(UserType.INSTRUCTOR,
                "any-name",
                "test@email.com",
                "any-login",
                "pass-123",
                "any-phone-number",
                buildAddressDTO(),
                null,
                "any-cref",
                "any-specialty",
                null);
    }
    public static CreateUserInput buildManagerInput() {
        return new CreateUserInput(UserType.MANAGER,
                "any-name",
                "test@email.com",
                "any-login",
                "pass-123",
                "any-phone-number",
                buildAddressDTO(),
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
                buildAddressDTO(),
                null,
                null,
                null,
                birthDate);
    }

    public static Instructor buildInstructor() {
        UUID uuid = UUID.randomUUID();
        return Instructor.restore(uuid,
                "any-name",
                "test@email.com",
                "any-login",
                "pass-123",
                "any-phone-number",
                buildAddress(),
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now(),
                "any-cref",
                "any-specialty");
    }

    public static Manager buildManager() {
        UUID uuid = UUID.randomUUID();
        return Manager.restore(uuid,
                "any-name",
                "test@email.com",
                "any-login",
                "pass-123",
                "any-phone-number",
                buildAddress(),
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now(),
                "any-gym-name");
    }

    public static Student buildStudent() {
        UUID uuid = UUID.randomUUID();
        LocalDate birthDate = LocalDate.now().minusYears(18);
        return Student.restore(uuid,
                "any-name",
                "test@email.com",
                "any-login",
                "pass-123",
                "any-phone-number",
                buildAddress(),
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now(),
                birthDate,
                true);
    }

    public static AddressDTO buildAddressDTO() {
        return new AddressDTO("any-street",
                "any-number",
                "any-neighborhood",
                "any-zip-code",
                "any-city",
                "any-state");
    }

    private static Address buildAddress() {
        return Address.newAddress("any-street",
                "any-number",
                "any-neighborhood",
                "any-zip-code",
                "any-city",
                "any-state");
    }
}
