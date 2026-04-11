package com.lucas.gym_management.user.factory;

import com.lucas.gym_management.user.application.domain.model.Instructor;
import com.lucas.gym_management.user.application.domain.model.Manager;
import com.lucas.gym_management.user.application.domain.model.Student;
import com.lucas.gym_management.user.application.domain.model.UserType;
import com.lucas.gym_management.user.application.domain.model.valueObjects.Address;
import com.lucas.gym_management.user.application.dto.AddressDTO;
import com.lucas.gym_management.user.application.ports.inbound.create.CreateUserInput;
import com.lucas.gym_management.user.application.ports.inbound.update.UpdateUserInput;
import com.lucas.gym_management.user.infrastructure.adapters.inbound.rest.dtos.AddressRestDTO;
import com.lucas.gym_management.user.infrastructure.adapters.inbound.rest.dtos.request.CreateUserRequest;
import com.lucas.gym_management.user.infrastructure.adapters.inbound.rest.dtos.request.UpdateUserRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserFactory {

    public static CreateUserInput buildInstructorInput() {
        return new CreateUserInput(UserType.INSTRUCTOR,
                "any-instructor-name",
                "instructor@email.com",
                "any-login-instructor",
                "pass-123",
                "instructor-phone-number",
                buildAddressDTO(),
                null,
                "any-cref",
                "any-specialty",
                null);
    }
    public static CreateUserInput buildManagerInput() {
        return new CreateUserInput(UserType.MANAGER,
                "any-manager-name",
                "manager@email.com",
                "any-login-manager",
                "pass-123",
                "manager-phone-number",
                buildAddressDTO(),
                "any-gym-name",
                null,
                null,
                null);
    }

    public static CreateUserInput buildStudentInput(){
        LocalDate birthDate = LocalDate.now().minusYears(18);
        return new CreateUserInput(UserType.STUDENT,
                "any-student-name",
                "student@email.com",
                "any-login-student",
                "pass-123",
                "student-phone-number",
                buildAddressDTO(),
                null,
                null,
                null,
                birthDate);
    }

    public static Instructor buildInstructor() {
        UUID uuid = UUID.randomUUID();
        return Instructor.restore(uuid,
                "any-instructor-name",
                "instructor@email.com",
                "any-login-instructor",
                "pass-123",
                "instructor-phone-number",
                buildAddress(),
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now(),
                "any-cref",
                "any-specialty");
    }

    public static Manager buildManager() {
        UUID uuid = UUID.randomUUID();
        return Manager.restore(uuid,
                "any-manager-name",
                "manager@email.com",
                "any-login-manager",
                "pass-123",
                "manager-phone-number",
                buildAddress(),
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now(),
                "any-gym-name");
    }

    public static Student buildStudent() {
        UUID uuid = UUID.randomUUID();
        LocalDate birthDate = LocalDate.now().minusYears(18);
        return Student.restore(uuid,
                "any-student-name",
                "student@email.com",
                "any-login-student",
                "any-password",
                "student-phone-number",
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

    public static Address buildAddress() {
        return Address.newAddress("any-street",
                "any-number",
                "any-neighborhood",
                "any-zip-code",
                "any-city",
                "any-state");
    }

    public static UpdateUserInput buildUpdateStudentInput() {
        return new UpdateUserInput(
                "updated-student-name",
                "updated@email.com",
                "updated-phone",
                new AddressDTO("updated-street",
                        "updated-number",
                        "updated-neighborhood",
                        "updated-zip-code",
                        "updated-city",
                        "updated-state"),
                null,
                null,
                null,
                LocalDate.now().minusYears(19),
                false);
    }

    public static Student buildUpdatedStudent(UUID id) {
        return Student.restore(id,
                "updated-student-name",
                "updated@email.com",
                "any-login-student",
                "any-password",
                "updated-phone",
                Address.newAddress("updated-street",
                        "updated-number",
                        "updated-neighborhood",
                        "updated-zip-code",
                        "updated-city",
                        "updated-state"),
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now(),
                LocalDate.now().minusYears(19),
                false);
    }

    public static CreateUserRequest createUserRequest() {
        return new CreateUserRequest("STUDENT",
                "any-student-name",
                "student@email.com",
                "any-login-student",
        "any-password",
                "any-phone-num",
                buildAddressRestDTO(),
                null,
                null,
                null,
                LocalDate.now().minusYears(18));
    }

    private static AddressRestDTO buildAddressRestDTO() {
        return new AddressRestDTO("any-street-name",
                "any-number",
                "any-neighborhood",
                "any-zip-code",
                "any-city",
                "any-state");
    }

    public static UpdateUserRequest buildUpdateStudentRequest() {
        return new UpdateUserRequest("Update-student-name",
                "update@email.com",
                "update-phone-num",
                new AddressRestDTO("update-street-name",
                        "update-number",
                        "update-neighborhood",
                        "update-zip-code",
                        "update-city",
                        "update-state"),
        null,
                null,
                null,
                LocalDate.of(2001, 02, 02),
                true);
    }
}
