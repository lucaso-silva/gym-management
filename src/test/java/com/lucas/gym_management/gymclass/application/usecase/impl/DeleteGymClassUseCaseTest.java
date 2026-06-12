package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.exceptions.ActionNotAllowedException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteGymClassUseCaseTest {
    @Mock
    private GymClassRepository gymClassRepository;

    @InjectMocks
    private DeleteGymClassUseCaseImpl deleteGymClassUseCase;

    @Test
    void shouldDeleteAGymClass_whenIdIsValidAndEnrolledStudentListIsEmpty(){
        var gymClass = GymClassFactory.buildGymClass();
        var gymClassId = gymClass.getId();

        when(gymClassRepository.findById(gymClassId))
                .thenReturn(Optional.of(gymClass));

        deleteGymClassUseCase.deleteGymClassById(gymClassId);

        verify(gymClassRepository).findById(gymClassId);
        verify(gymClassRepository).deleteById(gymClassId);
        verifyNoMoreInteractions(gymClassRepository);
    }

    @Test
    void shouldThrowNotFoundException_whenGymClassIdIsNotValid(){
        var gymClassId = UUID.randomUUID();

        when(gymClassRepository.findById(gymClassId))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                ()-> deleteGymClassUseCase.deleteGymClassById(gymClassId)
        );

        assertEquals("There is no gym class with id %s".formatted(gymClassId), exception.getMessage());

        verify(gymClassRepository).findById(gymClassId);
        verify(gymClassRepository, never()).deleteById(gymClassId);
        verifyNoMoreInteractions(gymClassRepository);
    }

    @Test
    void shouldThrowActionNotAllowedException_whenGymClassHasEnrolledStudents(){
        var gymClass = GymClassFactory.buildGymClass();
        var gymClassId = gymClass.getId();
        var studentId = UUID.randomUUID();

        gymClass.enrollStudent(studentId);

        when(gymClassRepository.findById(gymClassId))
                .thenReturn(Optional.of(gymClass));

        ActionNotAllowedException exception = assertThrows(
                ActionNotAllowedException.class,
                ()-> deleteGymClassUseCase.deleteGymClassById(gymClassId)
        );

        assertEquals("You cannot delete a gym class with enrolled students", exception.getMessage());

        verify(gymClassRepository).findById(gymClassId);
        verify(gymClassRepository, never()).deleteById(gymClassId);
        verifyNoMoreInteractions(gymClassRepository);
    }

}
