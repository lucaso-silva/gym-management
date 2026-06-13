package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.exceptions.ApplicationException;
import com.lucas.gym_management.gym.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import com.lucas.gym_management.gym.application.ports.outbound.repository.UserGateway;
import com.lucas.gym_management.gym.factory.GymFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveMemberUseCaseTest {

    @Mock
    private GymRepository gymRepository;

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private RemoveMemberUseCaseImpl removeMemberUseCase;

    @Test
    void shouldRemoveAMember_whenMemberExists(){
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var memberId = UUID.randomUUID();
        var userId = UUID.randomUUID();

        gym.addMember(memberId);

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));
        when(userGateway.canUserBeRemoved(memberId))
                .thenReturn(true);
        when(gymRepository.save(any(Gym.class)))
                .thenReturn(gym);

        assertEquals(1, gym.getMembersIds().size());
        assertTrue(gym.getMembersIds().contains(memberId));

        var output = removeMemberUseCase.removeMember(userId,gymId,memberId);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(gym.getId(), output.gymId()),
                ()-> assertEquals(gym.getName(), output.name()),
                ()-> assertEquals(gym.getPhone(), output.phone()),
                ()-> assertEquals(0, output.members())
        );

        verify(gymRepository).findById(gymId);
        verify(gymRepository).save(any(Gym.class));
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldThrowGymNotFoundException_whenGymIdIsNotValid(){
        var gymId = UUID.randomUUID();
        var memberId = UUID.randomUUID();
        var userId = UUID.randomUUID();

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.empty());

        GymNotFoundException exception = assertThrows(
                GymNotFoundException.class,
                () -> removeMemberUseCase.removeMember(userId,gymId,memberId)
        );

        assertEquals("Gym not found with id: %s".formatted(gymId),exception.getMessage());

        verify(gymRepository).findById(any(UUID.class));
        verify(userGateway, never()).canUserBeRemoved(any(UUID.class));
        verify(gymRepository, never()).save(any(Gym.class));
        verifyNoMoreInteractions(gymRepository);
    }

    //TODO: @Test void shouldThrowApplicationException_whenMemberCannotBeRemoved()

    @Test
    void shouldThrowDomainException_whenMemberDoesNotExist(){
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var memberId = UUID.randomUUID();
        var anotherMemberId = UUID.randomUUID();
        var userId = UUID.randomUUID();

        gym.addMember(memberId);

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));
        when(userGateway.canUserBeRemoved(anotherMemberId))
                .thenReturn(false);

        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> removeMemberUseCase.removeMember(userId,gymId,anotherMemberId)
        );

        assertEquals("Member cannot not be removed, verify if are they an active student, instructor or manage", exception.getMessage());

        verify(gymRepository).findById(any(UUID.class));
        verify(gymRepository, never()).save(any(Gym.class));
        verifyNoMoreInteractions(gymRepository);
    }
}
