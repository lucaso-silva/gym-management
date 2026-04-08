package com.lucas.gym_management.gym.application.domain.model;

import com.lucas.gym_management.gym.application.domain.model.exceptions.DomainException;
import com.lucas.gym_management.gym.application.domain.model.exceptions.GymClassNotAssociatedException;
import com.lucas.gym_management.gym.application.domain.model.exceptions.UserNotMemberException;
import com.lucas.gym_management.gym.application.domain.model.valueObjects.GymAddress;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class Gym {
    private final UUID id;
    private String name;
    private String phone;
//    private UUID managerId;
    private Set<UUID> membersIds;
    private Set<UUID> gymClassesIds;
    private GymAddress address;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Gym(String name, String phone, GymAddress address) {
        this.id = UUID.randomUUID();
        renameTo(name);
        updatePhone(phone);
        this.membersIds = new HashSet<>();
        this.gymClassesIds = new HashSet<>();
        updateAddress(address);
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    protected Gym(UUID id, String name, String phone, Set<UUID> membersIds, Set<UUID> gymClassesIds, GymAddress address, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.membersIds = new HashSet<>(membersIds);
        this.gymClassesIds = new HashSet<>(gymClassesIds);
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Gym createNewGym(String name, String phone, GymAddress address){
        return new Gym(name, phone, address);
    }

    public static Gym restoreGym(UUID id, String name, String phone, Set<UUID> membersIds, Set<UUID> gymClassesIds, GymAddress address, LocalDateTime createdAt, LocalDateTime updatedAt){
        return new Gym(id, name, phone, membersIds, gymClassesIds, address, createdAt, updatedAt);
    }

    public void renameTo(String newName){
        if(newName == null || newName.isEmpty()){
            throw new DomainException("Name cannot be empty");
        }
        this.name = newName;
        updatedAt = LocalDateTime.now();
    }

    public void updatePhone(String newPhone) {
        if(newPhone == null || newPhone.isEmpty()){
            throw new DomainException("Phone cannot be empty");
        }
        this.phone = newPhone;
        updatedAt = LocalDateTime.now();
    }

    public void updateAddress(GymAddress newAddress){
        if(newAddress == null){
            throw new DomainException("Address cannot be empty");
        }
        this.address = newAddress;
        updatedAt = LocalDateTime.now();
    }

    public void addMember(UUID userId) {
        if(userId == null){
            throw new DomainException("Member id cannot be empty");
        }
        if(membersIds.contains(userId)){
            throw new DomainException("Member already exists");
        }
        membersIds.add(userId);
        updatedAt = LocalDateTime.now();
    }

    public void removeMember(UUID userId) {
        if(userId == null){
            throw new DomainException("Member id cannot be empty");
        }
        if(!membersIds.contains(userId)){
            throw new UserNotMemberException("User is not a gym member");
        }
        membersIds.remove(userId);
        updatedAt = LocalDateTime.now();
    }

    public void addGymClass(UUID gymClassId) {
        if(gymClassId == null){
            throw new DomainException("Gym class id cannot be empty");
        }
        if(gymClassesIds.contains(gymClassId)){
            throw new DomainException("Gym class already exists");
        }
        gymClassesIds.add(gymClassId);
        updatedAt = LocalDateTime.now();
    }

    public void removeGymClass(UUID gymClassId) {
        if(gymClassId == null){
            throw new DomainException("Gym class id cannot be empty");
        }
        if(!gymClassesIds.contains(gymClassId)){
            throw new GymClassNotAssociatedException("Gym Class doesn't exist");
        }
        gymClassesIds.remove(gymClassId);
        updatedAt = LocalDateTime.now();
    }
}
