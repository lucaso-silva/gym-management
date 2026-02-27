package com.lucas.gym_management.infrastructure.adapters.outbound.persistence.mapper;

import com.lucas.gym_management.application.domain.model.*;
import com.lucas.gym_management.application.domain.model.valueObjects.Address;
import com.lucas.gym_management.infrastructure.adapters.outbound.persistence.entities.AddressJPA;
import com.lucas.gym_management.infrastructure.adapters.outbound.persistence.entities.UserJPAEntity;
import com.lucas.gym_management.infrastructure.adapters.outbound.persistence.entities.UserTypeJPA;

import java.util.HashMap;
import java.util.Map;

public class UserJPAMapper {
    public static UserJPAEntity toEntity(User user) {
        AddressJPA addressJPA = toAddressEntity(user.getAddress());
        UserTypeJPA userType = UserTypeJPA.valueOf(user.getUserType().name());

        UserJPAEntity userEntity = UserJPAEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .password(user.getPassword())
                .phone(user.getPhone())
                .address(addressJPA)
                .userType(userType)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        if(user instanceof Instructor) {
            userEntity.setCref(((Instructor) user).getCref());
            userEntity.setSpecialty(((Instructor) user).getSpecialty());
        }
        if(user instanceof Manager) {
            userEntity.setGymName(((Manager) user).getGymName());
        }
        if(user instanceof Student) {
            userEntity.setBirthDate(((Student) user).getBirthDate());
            userEntity.setActiveMembership(((Student) user).isActiveMembership());
        }

        return userEntity;
    }

    public static User toDomain(UserJPAEntity userJPAEntity) {
        Map<String, Object> extraFields = new HashMap<>();
        extraFields.put("birthDate", userJPAEntity.getBirthDate());
        extraFields.put("activeMembership", userJPAEntity.getActiveMembership());
        extraFields.put("cref", userJPAEntity.getCref());
        extraFields.put("specialty", userJPAEntity.getSpecialty());
        extraFields.put("gymName", userJPAEntity.getGymName());

        UserType userType = UserType.valueOf(userJPAEntity.getUserType().name());

        Address address = toAddressDomain(userJPAEntity.getAddress());

        return User.restore(userJPAEntity.getId(),
                userType,
                userJPAEntity.getName(),
                userJPAEntity.getEmail(),
                userJPAEntity.getLogin(),
                userJPAEntity.getPassword(),
                userJPAEntity.getPhone(),
                address,
                userJPAEntity.getCreatedAt(),
                userJPAEntity.getUpdatedAt(),
                extraFields);
    }

    private static AddressJPA toAddressEntity(Address address) {
        return new AddressJPA(address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getZipCode(),
                address.getCity(),
                address.getState());
    }

    private static Address toAddressDomain(AddressJPA addressJPA) {
        return Address.newAddress(addressJPA.getStreet(),
                addressJPA.getNumber(),
                addressJPA.getNeighborhood(),
                addressJPA.getZipCode(),
                addressJPA.getCity(),
                addressJPA.getState());
    }
}
