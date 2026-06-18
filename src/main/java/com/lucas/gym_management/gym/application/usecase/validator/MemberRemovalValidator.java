package com.lucas.gym_management.gym.application.usecase.validator;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.exceptions.ActiveMembershipException;
import com.lucas.gym_management.gym.application.exceptions.InstructorAssignedExeption;
import com.lucas.gym_management.gym.application.exceptions.ManagerAssignedToGymException;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymClassGateway;
import com.lucas.gym_management.gym.application.ports.outbound.repository.UserGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class MemberRemovalValidator {

    private UserGateway userGateway;
    private GymClassGateway gymClassGateway;

    public void verifyMemberRemoval(Gym gym, UUID memberId) {
        if(gym.getManagerId() != null &&
                gym.getManagerId().equals(memberId)){
            throw new ManagerAssignedToGymException("Active manager cannot be removed from the gym");
        }

        if(userGateway.studentHasActiveMembership(memberId)){
            throw new ActiveMembershipException("Member has active membership");
        }

        if(gymClassGateway.instructorHasAssignedClasses(gym.getId(), memberId)){
            throw new InstructorAssignedExeption("Instructor assigned to gym class cannot be removed");
        }
    }
}
