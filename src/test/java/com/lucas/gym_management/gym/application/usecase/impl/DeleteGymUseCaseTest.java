package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.exceptions.ApplicationException;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import com.lucas.gym_management.gym.factory.GymFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteGymUseCaseTest {

    @Mock
    private GymRepository gymRepository;

    @InjectMocks
    private DeleteGymUseCaseImpl deleteGymUseCase;

    @Test
    void shouldDeleteAGym_whenMembersListAndGymClassesListAreEmpty(){
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var userId = UUID.randomUUID();

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));

        deleteGymUseCase.deleteGymById(userId, gymId);

        verify(gymRepository).findById(any(UUID.class));
        verify(gymRepository).deleteById(gymId);
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldThrowApplicationException_whenMemberListIsNotEmpty(){
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var userId = UUID.randomUUID();
        var memberId = UUID.randomUUID();

        gym.addMember(memberId);

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));

        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> deleteGymUseCase.deleteGymById(userId, gymId)
        );

        assertEquals("You cannot delete a gym with active members", exception.getMessage());

        verify(gymRepository).findById(any(UUID.class));
        verify(gymRepository, never()).deleteById(any(UUID.class));
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldThrowApplicationException_whenGymClassesListIsNotEmpty(){
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var userId = UUID.randomUUID();
        var gymClassId = UUID.randomUUID();

        gym.addGymClass(gymClassId);

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));

        ApplicationException exception = assertThrows(
                ApplicationException.class,
                () -> deleteGymUseCase.deleteGymById(userId, gymId)
        );

        assertEquals("You cannot delete a gym with active classes", exception.getMessage());

        verify(gymRepository).findById(any(UUID.class));
        verify(gymRepository, never()).deleteById(any(UUID.class));
        verifyNoMoreInteractions(gymRepository);
    }
}
