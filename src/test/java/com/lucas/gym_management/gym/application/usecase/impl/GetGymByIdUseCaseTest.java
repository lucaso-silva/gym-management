package com.lucas.gym_management.gym.application.usecase.impl;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetGymByIdUseCaseTest {
    @Mock
    private GymRepository gymRepository;

    @InjectMocks
    private GetGymByIdUseCaseImpl getGymByIdUseCase;

    @Test
    void shouldReturnGymById_whenGymExists() {
        var gym = GymFactory.buildGym();
        UUID gymId = gym.getId();

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));

        var output = getGymByIdUseCase.getGymById(gymId);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(gymId, output.uuid()),
                ()-> assertEquals(gym.getName(), output.name()),
                ()-> assertEquals(gym.getPhone(), output.phone()),
                ()-> assertEquals(gym.getMembersIds().size(), output.members()),
                ()-> assertEquals(gym.getGymClassesIds().size(), output.activeClasses()),
                ()-> assertEquals(gym.getAddress().getStreet(), output.address().street()),
                ()-> assertEquals(gym.getAddress().getNumber(), output.address().number()),
                ()-> assertEquals(gym.getAddress().getNeighborhood(), output.address().neighborhood()),
                ()-> assertEquals(gym.getAddress().getCity(), output.address().city()),
                ()-> assertEquals(gym.getAddress().getState(), output.address().state())
        );

        verify(gymRepository).findById(gymId);
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldThrowGymNotFoundException_whenGymDoesNotExist() {
        var gymId = UUID.randomUUID();

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.empty());

        GymNotFoundException exception = assertThrows(
                GymNotFoundException.class,
                () -> getGymByIdUseCase.getGymById(gymId)
        );

        assertEquals("Gym not found with id %s".formatted(gymId), exception.getMessage());

        verify(gymRepository).findById(gymId);
        verifyNoMoreInteractions(gymRepository);
    }
}
