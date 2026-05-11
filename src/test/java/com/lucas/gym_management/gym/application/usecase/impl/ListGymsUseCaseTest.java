package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import com.lucas.gym_management.gym.factory.GymFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListGymsUseCaseTest {
    @Mock
    private GymRepository gymRepository;

    @InjectMocks
    private ListGymsUseCaseImpl listGymsUseCase;

    @Test
    void shouldReturnAListOfGyms_whenThereAreGyms() {
        var gymList = GymFactory.buildListOfGyms();

        when(gymRepository.findAll()).thenReturn(gymList);

        var outlist = listGymsUseCase.listGyms();

        assertNotNull(outlist);
        assertEquals(2, outlist.size());

        assertAll(
                () -> assertEquals(gymList.getFirst().getId(), outlist.getFirst().id()),
                () -> assertEquals(gymList.getFirst().getName(), outlist.getFirst().name()),
                () -> assertEquals(gymList.getFirst().getPhone(), outlist.getFirst().phone()),

                () -> assertEquals(gymList.getLast().getId(), outlist.getLast().id()),
                () -> assertEquals(gymList.getLast().getName(), outlist.getLast().name()),
                () -> assertEquals(gymList.getLast().getPhone(), outlist.getLast().phone())
        );

        verify(gymRepository).findAll();
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldReturnEmptyList_whenThereAreNoGyms() {

        when(gymRepository.findAll()).thenReturn(List.of());

        var outlist = listGymsUseCase.listGyms();

        assertNotNull(outlist);
        assertTrue(outlist.isEmpty());

        verify(gymRepository).findAll();
        verifyNoMoreInteractions(gymRepository);
    }
}