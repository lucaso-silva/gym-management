package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.domain.model.exceptions.UserNotMemberException;
import com.lucas.gym_management.gym.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
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
        when(gymRepository.save(any(Gym.class)))
                .thenReturn(gym);

        assertEquals(1, gym.getMembersIds().size());

        var output = removeMemberUseCase.removeMember(userId,gymId,memberId);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(gym.getId(), output.uuid()),
                ()-> assertEquals(gym.getName(), output.name()),
                ()-> assertEquals(gym.getPhone(), output.phone()),
                ()-> assertEquals(gym.getMembersIds().size(), output.members()),
                ()-> assertEquals(0, output.members()),
                ()-> assertEquals(gym.getGymClassesIds().size(), output.activeClasses())
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
        verify(gymRepository, never()).save(any(Gym.class));
        verifyNoMoreInteractions(gymRepository);
    }

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

        UserNotMemberException exception = assertThrows(
                UserNotMemberException.class,
                () -> removeMemberUseCase.removeMember(userId,gymId,anotherMemberId)
        );

        assertEquals("User is not a gym member", exception.getMessage());

        verify(gymRepository).findById(any(UUID.class));
        verify(gymRepository, never()).save(any(Gym.class));
        verifyNoMoreInteractions(gymRepository);
    }
}
