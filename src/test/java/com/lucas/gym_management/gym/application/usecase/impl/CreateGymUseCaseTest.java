package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymOutput;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import com.lucas.gym_management.gym.factory.GymFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateGymUseCaseTest {

    @Mock
    private GymRepository gymRepository;

    @InjectMocks
    private CreateGymUseCaseImpl createGymUseCase;

    @Test
    void shouldCreateGym_whenDataIsValid() {
        var gymInput = GymFactory.buildCreateGymInput();
        var gym = GymFactory.buildGym();

        when(gymRepository.save(any(Gym.class)))
                .thenReturn(gym);

        CreateGymOutput output = createGymUseCase.createGym(gymInput);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(gym.getId(), output.id()),
                () -> assertEquals(gym.getName(), output.name()),
                () -> assertEquals(gym.getPhone(), output.phone())
        );

        verify(gymRepository).save(any(Gym.class));
        verifyNoMoreInteractions(gymRepository);
    }
}
