package com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence.mapper;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.domain.model.valueObjects.GymAddress;
import com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence.entities.GymAddressJPA;
import com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence.entities.GymJPAEntity;

import java.util.HashSet;

public class GymJPAMapper {

    public static GymJPAEntity toEntity(Gym gym){
        return GymJPAEntity.builder()
                .id(gym.getId())
                .name(gym.getName())
                .phone(gym.getPhone())
                .membersIds(new HashSet<>(gym.getMembersIds()))
                .gymClassesIds(new HashSet<>(gym.getGymClassesIds()))
                .address(toGymAddressEntity(gym.getAddress()))
                .createdAt(gym.getCreatedAt())
                .updatedAt(gym.getUpdatedAt())
                .build();
    };

    public static Gym toDomain(GymJPAEntity entity){
        return Gym.restoreGym(entity.getId(),
                entity.getName(),
                entity.getPhone(),
                entity.getMembersIds(),
                entity.getGymClassesIds(),
                toGymAddressDomain(entity.getAddress()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    };

    private static GymAddressJPA toGymAddressEntity(GymAddress address){
        if(address == null) return null;

        return GymAddressJPA.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .build();
    }

    private static GymAddress toGymAddressDomain(GymAddressJPA addressJPA){
        if(addressJPA == null) return null;

        return GymAddress.newAddress(addressJPA.getStreet(),
                addressJPA.getNumber(),
                addressJPA.getNeighborhood(),
                addressJPA.getCity(),
                addressJPA.getState());
    }
}
