package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;
import com.lucas.gym_management.gymclass.application.exceptions.BusinessException;
import com.lucas.gym_management.gymclass.application.exceptions.NotFoundException;
import com.lucas.gym_management.gymclass.application.ports.inbound.manage_students.EnrollStudentInput;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.application.usecase.validator.GymMemberValidator;
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
class EnrollStudentUseCaseTest {
    @Mock
    private GymClassRepository gymClassRepository;

    @Mock
    private GymMemberValidator gymMemberValidator;

    @InjectMocks
    private EnrollStudentUseCaseImpl enrollStudentUseCase;

    @Test
    void shouldEnrollStudent_whenIdsAreValid(){
        var gymClass = GymClassFactory.buildGymClass();
        var gymClassId = gymClass.getId();
        var input = new EnrollStudentInput(UUID.randomUUID());
        var studentId = input.studentId();
        var gymId = gymClass.getGymId();

        when(gymClassRepository.findById(gymClassId))
                .thenReturn(Optional.of(gymClass));
        when(gymMemberValidator.isActiveStudentFromGym(gymId, studentId))
                .thenReturn(true);
        when(gymClassRepository.save(gymClass))
                .thenReturn(gymClass);

        assertEquals(0, gymClass.getEnrolledStudents().size());

        var output = enrollStudentUseCase.enrollStudent(gymClassId, input);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(gymClass.getId(), output.id()),
                ()-> assertEquals(gymClass.getName(), output.name()),
                ()-> assertEquals(gymClass.getCapacity(), output.capacity()),
                ()-> assertEquals(gymClass.getEnrolledStudents().size(), output.numEnrolledStudents())
        );

        verify(gymClassRepository).findById(gymClassId);
        verify(gymMemberValidator).isActiveStudentFromGym(gymId, studentId);
        verify(gymClassRepository).save(gymClass);
        verifyNoMoreInteractions(gymMemberValidator);
        verifyNoMoreInteractions(gymClassRepository);

    }

    @Test
    void shouldThrowNotFoundException_whenGymClassIdIsInvalid(){
        var gymClassId = UUID.randomUUID();
        var input = new EnrollStudentInput(UUID.randomUUID());

        when(gymClassRepository.findById(gymClassId))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                ()-> enrollStudentUseCase.enrollStudent(gymClassId,input)
        );

        assertEquals("There is no gym class with id %s".formatted(gymClassId), exception.getMessage());

        verify(gymClassRepository).findById(gymClassId);
        verify(gymMemberValidator, never()).isActiveStudentFromGym(any(UUID.class), any(UUID.class));
        verify(gymClassRepository, never()).save(any(GymClass.class));
        verifyNoMoreInteractions(gymClassRepository);
    }

    @Test
    void shouldThrowBusinessException_whenStudentIdIsInvalid(){
        var gymClass = GymClassFactory.buildGymClass();
        var gymClassId = gymClass.getId();
        var input = new EnrollStudentInput(UUID.randomUUID());
        var studentId = input.studentId();
        var gymId = gymClass.getGymId();

        when(gymClassRepository.findById(gymClassId))
                .thenReturn(Optional.of(gymClass));
        when(gymMemberValidator.isActiveStudentFromGym(gymId, studentId))
                .thenReturn(false);

        BusinessException exception = assertThrows(
                BusinessException.class,
                ()-> enrollStudentUseCase.enrollStudent(gymClassId, input)
        );

        assertEquals("Only active students can be enrolled in a class", exception.getMessage());

        verify(gymClassRepository).findById(gymClassId);
        verify(gymMemberValidator).isActiveStudentFromGym(gymId, studentId);
        verify(gymClassRepository, never()).save(any(GymClass.class));
        verifyNoMoreInteractions(gymMemberValidator);
        verifyNoMoreInteractions(gymClassRepository);
    }
}
