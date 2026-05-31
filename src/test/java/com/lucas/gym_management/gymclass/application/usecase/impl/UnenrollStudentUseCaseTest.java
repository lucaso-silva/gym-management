package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnenrollStudentUseCaseTest {
    @Mock
    private GymClassRepository gymClassRepository;

    @InjectMocks
    private UnenrollStudentUseCaseImpl unenrollStudentUseCase;

    @Test
    void shouldUnenrollStudent_whenIdIsValid(){
        var gymClass = GymClassFactory.buildGymClass();
        var studentId = UUID.randomUUID();
        var gymClassId = gymClass.getId();

        gymClass.enrollStudent(studentId);

        when(gymClassRepository.findById(gymClassId))
                .thenReturn(Optional.of(gymClass));
        when(gymClassRepository.save(gymClass))
                .thenReturn(gymClass);

        assertEquals(1, gymClass.getEnrolledStudents().size());

        var output = unenrollStudentUseCase.unenrollStudent(gymClassId, studentId);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(gymClass.getId(), output.id()),
                ()-> assertEquals(gymClass.getName(), output.name()),
                ()-> assertEquals(gymClass.getCapacity(), output.capacity()),
                ()-> assertEquals(0, output.numEnrolledStudents())
        );

        verify(gymClassRepository).findById(gymClassId);
        verify(gymClassRepository).save(any(GymClass.class));
        verifyNoMoreInteractions(gymClassRepository);

    }

    @Test
    void shouldThrowNotFoundException_whenGymClassIdIsInvalid(){
        var gymClassId = UUID.randomUUID();
        var studentId = UUID.randomUUID();

        when(gymClassRepository.findById(gymClassId))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                ()-> unenrollStudentUseCase.unenrollStudent(gymClassId,studentId)
        );

        assertEquals("There is no gym class with id %s".formatted(gymClassId), exception.getMessage());

        verify(gymClassRepository).findById(gymClassId);
        verify(gymClassRepository, never()).save(any(GymClass.class));
        verifyNoMoreInteractions(gymClassRepository);
    }
}
