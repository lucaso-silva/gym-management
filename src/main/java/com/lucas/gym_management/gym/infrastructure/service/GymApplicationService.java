package com.lucas.gym_management.gym.infrastructure.service;

import com.lucas.gym_management.gym.application.dto.GymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymInput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.delete.DeleteGymUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.get.GetGymByIdUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.list.ListGymOutput;
import com.lucas.gym_management.gym.application.ports.inbound.list.ListGymsUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.manage_gym_classes.AddGymClassInput;
import com.lucas.gym_management.gym.application.ports.inbound.manage_gym_classes.AddGymClassUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.manage_gym_classes.RemoveGymClassUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.manage_members.AddMemberUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.manage_members.AddMemberInput;
import com.lucas.gym_management.gym.application.ports.inbound.manage_members.RemoveMemberUseCase;
import com.lucas.gym_management.gym.application.ports.inbound.update.UpdateGymInput;
import com.lucas.gym_management.gym.application.ports.inbound.update.UpdateGymUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GymApplicationService {
    private final CreateGymUseCase createGymUseCase;
    private final GetGymByIdUseCase getGymByIdUseCase;
    private final ListGymsUseCase listGymsUseCase;
    private final UpdateGymUseCase updateGymUseCase;
    private final DeleteGymUseCase deleteGymUseCase;
    private final AddMemberUseCase addMemberUseCase;
    private final RemoveMemberUseCase removeMemberUseCase;
    private final AddGymClassUseCase addGymClassUseCase;
    private final RemoveGymClassUseCase removeGymClassUseCase;

    @Transactional
    public CreateGymOutput createGym(CreateGymInput input) {
        return createGymUseCase.createGym(input);
    }

    @Transactional(readOnly = true)
    public GymOutput getGymById(UUID id) {
        return getGymByIdUseCase.getGymById(id);
    }

    @Transactional(readOnly = true)
    public List<ListGymOutput> listGyms() {
        return listGymsUseCase.listGyms();
    }

    @Transactional
    public GymOutput updateGym(UUID userId, UUID gymId, UpdateGymInput input ) {
        return updateGymUseCase.updateGym(userId, gymId, input);
    }

    @Transactional
    public void deleteGymById(UUID userId, UUID id) {
        deleteGymUseCase.deleteGymById(userId, id);
    }

    @Transactional
    public GymOutput addMember(UUID userId, UUID gymId, AddMemberInput memberId) {
        return addMemberUseCase.addMember(userId, gymId, memberId);
    }

    @Transactional
    public GymOutput removeMember(UUID userId, UUID gymId, UUID memberId) {
        return removeMemberUseCase.removeMember(userId, gymId, memberId);
    }

    @Transactional
    public GymOutput addGymClass(UUID userId, UUID gymId, AddGymClassInput input) {
        return addGymClassUseCase.addGymClass(userId, gymId, input);
    }

    @Transactional
    public GymOutput removeGymClass(UUID userId, UUID gymId, UUID gymClassId) {
        return removeGymClassUseCase.removeGymClass(userId, gymId, gymClassId);
    }
}
