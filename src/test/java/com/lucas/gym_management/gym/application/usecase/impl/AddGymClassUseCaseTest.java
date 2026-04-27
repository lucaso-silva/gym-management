package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.domain.model.exceptions.DomainException;
import com.lucas.gym_management.gym.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gym.application.ports.inbound.manage_gym_classes.AddGymClassInput;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddGymClassUseCaseTest {

    @Mock
    private GymRepository gymRepository;

    @InjectMocks
    private AddGymClassUseCaseImpl addGymClassUseCase;

    @Test
    void shouldAddAClassToGym_whenDataIsValid(){
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var userId = UUID.randomUUID();
        var gymClassId = UUID.randomUUID();
        var addGymClassInput = new AddGymClassInput(gymClassId);

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));
        when(gymRepository.save(gym))
                .thenReturn(gym);

        assertTrue(gym.getGymClassesIds().isEmpty());

        var output = addGymClassUseCase.addGymClass(userId, gymId, addGymClassInput);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(gym.getId(), output.uuid()),
                ()-> assertEquals(gym.getName(), output.name()),
                ()-> assertEquals(gym.getPhone(), output.phone()),
                ()-> assertEquals(gym.getMembersIds().size(), output.members()),
                ()-> assertEquals(0, output.members()),
                ()-> assertEquals(gym.getGymClassesIds().size(), output.activeClasses()),
                () -> assertTrue(gym.getGymClassesIds().contains(gymClassId)),
                ()-> assertEquals(1, output.activeClasses())
        );

        verify(gymRepository).findById(gymId);
        verify(gymRepository).save(gym);
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldThrowGymNotFoundException_whenGymIdIsNotValid(){
        var gymId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var addGymClassInput = new AddGymClassInput(UUID.randomUUID());

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.empty());

        GymNotFoundException exception = assertThrows(
                GymNotFoundException.class,
                () -> addGymClassUseCase.addGymClass(userId, gymId, addGymClassInput));

        assertEquals("Gym not found with id: %s".formatted(gymId), exception.getMessage());

        verify(gymRepository).findById(gymId);
        verify(gymRepository, never()).save(any(Gym.class));
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldThrowDomainException_whenGymClassIsAlreadyAdded(){
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var userId = UUID.randomUUID();
        var gymClassId = UUID.randomUUID();
        var addGymClassInput = new AddGymClassInput(gymClassId);

        gym.addGymClass(gymClassId);

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));

        DomainException exception = assertThrows(
                DomainException.class,
                ()-> addGymClassUseCase.addGymClass(userId, gymId, addGymClassInput)
        );

        assertEquals("Gym class already exists", exception.getMessage());

        verify(gymRepository).findById(gymId);
        verify(gymRepository, never()).save(any(Gym.class));
        verifyNoMoreInteractions(gymRepository);
    }
}
