package com.lucas.gym_management.application.dto.user;

import com.lucas.gym_management.application.domain.model.*;
import com.lucas.gym_management.application.dto.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@ToString
@Getter
public abstract class UserOutput {
    private final UUID id;
    private final UserType userType;
    private final String name;
    private final String email;
    private final String login;
    private final String phone;
    private final AddressDTO address;

    public static UserOutput from(User user) {

        return switch (user) {
            case Student student -> {
                yield new StudentOutput(student.getId(),
                        student.getUserType(),
                        student.getName(),
                        student.getEmail(),
                        student.getLogin(),
                        student.getPhone(),
                        buildAddressDto(student),
                        student.getBirthDate(),
                        student.isActiveMembership());
            }
            case Instructor instructor -> {

                yield new InstructorOutput(instructor.getId(),
                        instructor.getUserType(),
                        instructor.getName(),
                        instructor.getEmail(),
                        instructor.getLogin(),
                        instructor.getPhone(),
                        buildAddressDto(instructor),
                        instructor.getCref(),
                        instructor.getSpecialty());
            }
            case Manager manager -> {

                yield  new ManagerOutput(manager.getId(),
                        manager.getUserType(),
                        manager.getName(),
                        manager.getEmail(),
                        manager.getLogin(),
                        manager.getPhone(),
                        buildAddressDto(manager),
                        manager.getGymName());
            }
            default -> throw new IllegalArgumentException("Unsupported user type: " + user.getClass());
        };
    }

    private static AddressDTO buildAddressDto(User user) {
        return new AddressDTO(user.getAddress().getStreet(),
                user.getAddress().getNumber(),
                user.getAddress().getNeighborhood(),
                user.getAddress().getZipCode(),
                user.getAddress().getCity(),
                user.getAddress().getState());
    }

}
