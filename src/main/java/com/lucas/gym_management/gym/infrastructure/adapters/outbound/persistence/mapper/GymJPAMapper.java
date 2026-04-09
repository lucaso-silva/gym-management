package com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence.mapper;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.domain.model.valueObjects.GymAddress;
import com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence.entities.GymAddressJPA;
import com.lucas.gym_management.gym.infrastructure.adapters.outbound.persistence.entities.GymJPAEntity;

import java.util.HashSet;
import java.util.Set;

public class GymJPAMapper {

    public static GymJPAEntity toEntity(Gym gym){
        var membersId = gym.getMembersIds() != null ?
                gym.getMembersIds() :
                Set.of();
        var gymClassesId = gym.getGymClassesIds() != null ?
                gym.getGymClassesIds() :
                Set.of();

        return GymJPAEntity.builder()
                .id(gym.getId())
                .name(gym.getName())
                .phone(gym.getPhone())
                .membersIds(new HashSet<>(membersId))
                .gymClassesIds(new HashSet<>(gymClassesId))
                .address(toGymAddressEntity(gym.getAddress()))
                .createdAt(gym.getCreatedAt())
                .updatedAt(gym.getUpdatedAt())
                .build();
    };

    public static Gym toDomain(GymJPAEntity entity){
        var membersId = entity.getMembersIds() != null ?
                entity.getMembersIds() :
                Set.of();
        var gymClassesId = entity.getGymClassesIds() != null ?
                entity.getGymClassesIds() :
                Set.of();

        return Gym.restoreGym(entity.getId(),
                entity.getName(),
                entity.getPhone(),
                new HashSet<>(membersId),
                new HashSet<>(gymClassesId),
                toGymAddressDomain(entity.getAddress()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    };

    private static GymAddressJPA toGymAddressEntity(GymAddress address){
        return GymAddressJPA.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .build();
    }

    private static GymAddress toGymAddressDomain(GymAddressJPA addressJPA){
        return GymAddress.newAddress(addressJPA.getStreet(),
                addressJPA.getNumber(),
                addressJPA.getNeighborhood(),
                addressJPA.getCity(),
                addressJPA.getState());
    }
}
