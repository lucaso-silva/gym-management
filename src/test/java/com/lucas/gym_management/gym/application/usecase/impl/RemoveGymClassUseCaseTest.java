package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.domain.model.exceptions.GymClassNotAssociatedException;
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
class RemoveGymClassUseCaseTest {

    @Mock
    private GymRepository gymRepository;

    @InjectMocks
    private RemoveGymClassUseCaseImpl removeGymClassUseCase;

    @Test
    void shouldRemoveAGymClass_whenGymClassIdIsValid() {
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var userId = UUID.randomUUID();
        var gymClassId = UUID.randomUUID();

        gym.addGymClass(gymClassId);

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));
        when(gymRepository.save(gym))
                .thenReturn(gym);

        assertEquals(1, gym.getGymClassesIds().size());
        assertTrue(gym.getGymClassesIds().contains(gymClassId));

        var output = removeGymClassUseCase.removeGymClass(userId,gymId,gymClassId);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(gym.getId(), output.uuid()),
                ()-> assertEquals(gym.getName(), output.name()),
                ()-> assertEquals(gym.getPhone(), output.phone()),
                ()-> assertEquals(gym.getMembersIds().size(), output.members()),
                ()-> assertTrue(gym.getMembersIds().isEmpty()),
                ()-> assertEquals(gym.getGymClassesIds().size(), output.activeClasses()),
                ()-> assertTrue(gym.getGymClassesIds().isEmpty())
        );

        verify(gymRepository).findById(gymId);
        verify(gymRepository).save(gym);
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldThrowGymNotFoundException_whenGymIdIsNotValid() {
        var gymId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var gymClassId = UUID.randomUUID();

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.empty());

        GymNotFoundException exception = assertThrows(
                GymNotFoundException.class,
                ()-> removeGymClassUseCase.removeGymClass(userId,gymId,gymClassId)
        );

        assertEquals("Gym not found with id: %s".formatted(gymId), exception.getMessage());

        verify(gymRepository).findById(gymId);
        verify(gymRepository, never()).save(any(Gym.class));
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldThrowGymClassNotAssociatedException_whenGymClassIdDoesNotExist() {
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var userId = UUID.randomUUID();
        var gymClassId = UUID.randomUUID();
        var anotherGymClassId = UUID.randomUUID();

        gym.addGymClass(gymClassId);

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));

        GymClassNotAssociatedException exception = assertThrows(
                GymClassNotAssociatedException.class,
                ()-> removeGymClassUseCase.removeGymClass(userId,gymId,anotherGymClassId)
        );

        assertEquals("Gym class doesn't exist", exception.getMessage());

        verify(gymRepository).findById(gymId);
        verify(gymRepository, never()).save(any(Gym.class));
        verifyNoMoreInteractions(gymRepository);
    }
}
