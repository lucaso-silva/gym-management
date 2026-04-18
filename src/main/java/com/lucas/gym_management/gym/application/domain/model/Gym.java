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
        if(name == null || name.isBlank() || phone == null || phone.isBlank() ||  address == null) {
            throw new DomainException("You must provide Gym name, phone and address to create a Gym");
        }
        this.id = UUID.randomUUID();
        this.name = name;
        this.phone = phone;
        this.membersIds = new HashSet<>();
        this.gymClassesIds = new HashSet<>();
        this.address = address;
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
        if(newName == null || newName.isBlank()){
            throw new DomainException("Name cannot be empty");
        }
        this.name = newName;
        updateInfo();
    }

    public void updatePhone(String newPhone) {
        if(newPhone == null || newPhone.isBlank()){
            throw new DomainException("Phone cannot be empty");
        }
        this.phone = newPhone;
        updateInfo();
    }

    public void updateAddress(String street, String number, String neighborhood, String city, String state) {
        boolean updated = false;

        if(street != null) {
            this.address.updateStreet(street);
            updated = true;
        }

        if(number != null) {
            this.address.updateNumber(number);
            updated = true;
        }

        if(neighborhood != null) {
            this.address.updateNeighborhood(neighborhood);
            updated = true;
        }

        if(city != null) {
            this.address.updateCity(city);
            updated = true;
        }

        if(state != null) {
            this.address.updateState(state);
            updated = true;
        }

        if(updated) {
            updateInfo();
        }
    }

    public void addMember(UUID memberId) {
        if(memberId == null){
            throw new DomainException("Member id cannot be empty");
        }
        if(membersIds.contains(memberId)){
            throw new DomainException("User is already a gym member");
        }
        membersIds.add(memberId);
        updateInfo();
    }

    public void removeMember(UUID memberId) {
        if(memberId == null){
            throw new DomainException("Member id cannot be empty");
        }
        if(!membersIds.contains(memberId)){
            throw new UserNotMemberException("User is not a gym member");
        }
        //TODO: implement rules: is user student (active membership?), instructor (is registered for any active class?), manager (is manager for any gym?)

        membersIds.remove(memberId);
        updateInfo();
    }

    public void addGymClass(UUID gymClassId) {
        if(gymClassId == null){
            throw new DomainException("Gym class id cannot be empty");
        }
        if(gymClassesIds.contains(gymClassId)){
            throw new DomainException("Gym class already exists");
        }
        gymClassesIds.add(gymClassId);
        updateInfo();
    }

    public void removeGymClass(UUID gymClassId) {
        if(gymClassId == null){
            throw new DomainException("Gym class id cannot be empty");
        }
        if(!gymClassesIds.contains(gymClassId)){
            throw new GymClassNotAssociatedException("Gym class doesn't exist");
        }
        gymClassesIds.remove(gymClassId);
        updateInfo();
    }

    private void updateInfo(){
        this.updatedAt = LocalDateTime.now();
    }
}
