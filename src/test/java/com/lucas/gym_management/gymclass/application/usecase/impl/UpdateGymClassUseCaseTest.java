package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.application.domain.model.GymClass;
import com.lucas.gym_management.gymclass.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gymclass.application.exceptions.InvalidMemberException;
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
class UpdateGymClassUseCaseTest {
    @Mock
    private GymClassRepository gymClassRepository;

    @Mock
    private GymMemberValidator gymMemberValidator;

    @InjectMocks
    private UpdateGymClassUseCaseImpl updateGymClassUseCase;

    @Test
    void shouldUpdateGymClass_whenDataIsValid(){
        var gymClass = GymClassFactory.buildGymClass();
        var newInstructorId = UUID.randomUUID();
        var updateGymClassInput = GymClassFactory.buildUpdateGymClassInput(newInstructorId);
        var gymClassId = gymClass.getId();
        var gymId = gymClass.getGymId();

        when(gymClassRepository.findById(gymClassId))
                .thenReturn(Optional.of(gymClass));
        when(gymMemberValidator.isInstructorFromGym(gymId, newInstructorId))
                .thenReturn(true);
        when(gymClassRepository.hasScheduleConflict(gymId, gymClassId, updateGymClassInput.schedule().dayOfWeek(), updateGymClassInput.schedule().room(), updateGymClassInput.schedule().startTime(), updateGymClassInput.schedule().endTime()))
                .thenReturn(false);
        when(gymClassRepository.save(any(GymClass.class)))
                .thenReturn(gymClass);

        var output = updateGymClassUseCase.updateGymClass(gymClassId, updateGymClassInput);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(gymClass.getId(), output.id()),
                ()-> assertEquals(updateGymClassInput.name(), output.name()),
                ()-> assertEquals(updateGymClassInput.capacity(), output.capacity()),
                ()-> assertEquals(updateGymClassInput.schedule().dayOfWeek(), output.schedule().dayOfWeek()),
                ()-> assertEquals(updateGymClassInput.schedule().room(), output.schedule().room()),
                ()-> assertEquals(updateGymClassInput.schedule().startTime(), output.schedule().startTime()),
                ()-> assertEquals(updateGymClassInput.schedule().endTime(), output.schedule().endTime())
        );

        verify(gymClassRepository).findById(gymClassId);
        verify(gymMemberValidator).isInstructorFromGym(gymId, newInstructorId);
        verify(gymClassRepository).hasScheduleConflict(gymId, gymClassId, updateGymClassInput.schedule().dayOfWeek(), updateGymClassInput.schedule().room(), updateGymClassInput.schedule().startTime(), updateGymClassInput.schedule().endTime());
        verify(gymClassRepository).save(gymClass);
        verifyNoMoreInteractions(gymMemberValidator);
        verifyNoMoreInteractions(gymClassRepository);
    }

    @Test
    void shouldThrowNotFoundException_whenGymClassIdIsNotValid(){
        var updateGymClassInput = GymClassFactory.buildUpdateGymClassInput(UUID.randomUUID());
        var gymClasId = UUID.randomUUID();

        when(gymClassRepository.findById(gymClasId))
                .thenReturn(Optional.empty());

        GymNotFoundException exception = assertThrows(
                GymNotFoundException.class,
                ()-> updateGymClassUseCase.updateGymClass(gymClasId, updateGymClassInput)
        );

        assertEquals("There is no gym class with the id %s".formatted(gymClasId), exception.getMessage());

        verify(gymClassRepository).findById(gymClasId);
        verify(gymMemberValidator, never()).isInstructorFromGym(any(UUID.class), any(UUID.class));
        verify(gymClassRepository, never()).save(any(GymClass.class));
        verifyNoMoreInteractions(gymClassRepository);
    }

    @Test
    void shouldThrowInvalidMemberIdException_whenInstructorIdIsNotValid(){
        var gymClass = GymClassFactory.buildGymClass();
        var instructorId = UUID.randomUUID();
        var updateGymClassInput = GymClassFactory.buildUpdateGymClassInput(instructorId);
        var gymClassId = gymClass.getId();
        var gymId = gymClass.getGymId();

        when(gymClassRepository.findById(gymClassId))
                .thenReturn(Optional.of(gymClass));
        when(gymMemberValidator.isInstructorFromGym(gymId, instructorId))
                .thenReturn(false);

        InvalidMemberException exception = assertThrows(
                InvalidMemberException.class,
                ()-> updateGymClassUseCase.updateGymClass(gymClassId, updateGymClassInput)
        );

        assertEquals("%s is not a valid instructor id".formatted(instructorId), exception.getMessage());

        verify(gymClassRepository).findById(gymClassId);
        verify(gymMemberValidator).isInstructorFromGym(gymId, instructorId);
        verify(gymClassRepository, never()).save(any(GymClass.class));
        verifyNoMoreInteractions(gymMemberValidator);
        verifyNoMoreInteractions(gymClassRepository);
    }
}
