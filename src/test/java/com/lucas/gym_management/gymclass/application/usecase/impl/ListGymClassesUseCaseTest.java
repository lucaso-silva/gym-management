package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.factory.GymClassFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListGymClassesUseCaseTest {
    @Mock
    private GymClassRepository gymClassRepository;

    @InjectMocks
    private ListGymClassesUseCaseImpl listGymClassesUseCase;

    @Test
    void shouldReturnAListOfGymClasses_whenThereAreGymClasses(){
        var gymClassesList = GymClassFactory.buildListOfGymClasses();

        when(gymClassRepository.findAll()).thenReturn(gymClassesList);

        var output = listGymClassesUseCase.listGymClasses();

        assertNotNull(output);
        assertEquals(2, output.size());

        assertAll(
                ()-> assertEquals(gymClassesList.getFirst().getName(), output.getFirst().name()),
                ()-> assertEquals(gymClassesList.getFirst().getSchedule().dayOfWeek(), output.getFirst().schedule().dayOfWeek()),
                ()-> assertEquals(gymClassesList.getFirst().getSchedule().room(), output.getFirst().schedule().room()),
                ()-> assertEquals(gymClassesList.getFirst().getSchedule().startTime(), output.getFirst().schedule().startTime()),
                ()-> assertEquals(gymClassesList.getFirst().getSchedule().endTime(), output.getFirst().schedule().endTime()),

                ()-> assertEquals(gymClassesList.getLast().getName(), output.getLast().name()),
                ()-> assertEquals(gymClassesList.getLast().getSchedule().dayOfWeek(), output.getLast().schedule().dayOfWeek()),
                ()-> assertEquals(gymClassesList.getLast().getSchedule().room(), output.getLast().schedule().room()),
                ()-> assertEquals(gymClassesList.getLast().getSchedule().startTime(), output.getLast().schedule().startTime()),
                ()-> assertEquals(gymClassesList.getLast().getSchedule().endTime(), output.getLast().schedule().endTime())
        );

        verify(gymClassRepository).findAll();
        verifyNoMoreInteractions(gymClassRepository);
    }

    @Test
    void shouldReturnAnEmptyList_whenThereAreNoGymClasses(){

        when(gymClassRepository.findAll()).thenReturn(Collections.emptyList());

        var output = listGymClassesUseCase.listGymClasses();

        assertNotNull(output);
        assertTrue(output.isEmpty());

        verify(gymClassRepository).findAll();
        verifyNoMoreInteractions(gymClassRepository);
    }
}

