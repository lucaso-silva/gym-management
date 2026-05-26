package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.exceptions.NotFoundException;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.factory.GymClassFactory;
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
class GetGymClassByIdUseCaseTest {
    @Mock
    private GymClassRepository gymClassRepository;

    @InjectMocks
    private GetGymClassByIdUseCaseImpl getGymClassByIdUseCase;

    @Test
    void shouldReturnGymClassById_whenIdIsValid(){
        var gymClass = GymClassFactory.buildGymClass();
        var gymClassId = gymClass.getId();

        when(gymClassRepository.findById(gymClassId))
                .thenReturn(Optional.of(gymClass));

        var output = getGymClassByIdUseCase.getGymClassById(gymClassId);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(gymClassId, output.id()),
                ()-> assertEquals(gymClass.getName(), output.name()),
                ()-> assertEquals(gymClass.getCapacity(), output.capacity()),
                ()-> assertEquals(gymClass.getEnrolledStudents().size(), output.numEnrolledStudents()),
                ()-> assertEquals(gymClass.getSchedule().dayOfWeek(), output.schedule().dayOfWeek()),
                ()-> assertEquals(gymClass.getSchedule().room(), output.schedule().room()),
                ()-> assertEquals(gymClass.getSchedule().startTime(), output.schedule().startTime()),
                ()-> assertEquals(gymClass.getSchedule().endTime(), output.schedule().endTime())
        );

        verify(gymClassRepository).findById(gymClassId);
        verifyNoMoreInteractions(gymClassRepository);
    }

    @Test
    void shouldThrowNotFoundException_whenIdIsNotValid(){
        var gymClassId = UUID.randomUUID();

        when(gymClassRepository.findById(gymClassId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                ()-> getGymClassByIdUseCase.getGymClassById(gymClassId)
        );

        assertEquals("Gym class not found with id %s".formatted(gymClassId), exception.getMessage());

        verify(gymClassRepository).findById(gymClassId);
        verifyNoMoreInteractions(gymClassRepository);
    }
}
