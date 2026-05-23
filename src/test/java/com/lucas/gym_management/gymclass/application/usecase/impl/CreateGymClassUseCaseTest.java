package com.lucas.gym_management.gymclass.application.usecase.impl;

import com.lucas.gym_management.gymclass.factory.GymClassFactory;
import com.lucas.gym_management.gymclass.application.domain.model.GymClass;
import com.lucas.gym_management.gymclass.application.exceptions.InvalidInstructorIdException;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymClassRepository;
import com.lucas.gym_management.gymclass.application.ports.outbound.repository.GymGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateGymClassUseCaseTest {
    @Mock
    private GymClassRepository gymClassRepository;

    @Mock
    private GymGateway gymGateway;

    @InjectMocks
    private CreateGymClassUseCaseImpl createGymClassUseCase;

    @Test
    void shouldCreateGymClass_whenDataIsValid(){
        var gymClassInput = GymClassFactory.buildCreateGymClassInput();
        var gymClass = GymClassFactory.buildGymClass();
        var instructorId = gymClassInput.instructorId();

        when(gymGateway.isValidInstructor(instructorId))
                .thenReturn(true);
        when(gymClassRepository.save(any(GymClass.class)))
                .thenReturn(gymClass);

        var output = createGymClassUseCase.createGymClass(gymClassInput);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(gymClass.getId(), output.id()),
                () -> assertEquals(gymClass.getName(), output.name()),
                () -> assertEquals(gymClass.getInstructorId(), output.instructorId()),
                () -> assertEquals(gymClass.getCapacity(), output.capacity()),
                () -> assertEquals(gymClass.getSchedule().dayOfWeek(), output.schedule().dayOfWeek()),
                () -> assertEquals(gymClass.getSchedule().room(), output.schedule().room()),
                () -> assertEquals(gymClass.getSchedule().startTime(), output.schedule().startTime()),
                () -> assertEquals(gymClass.getSchedule().endTime(), output.schedule().endTime())
        );

        verify(gymGateway).isValidInstructor(instructorId);
        verify(gymClassRepository).save(any(GymClass.class));
        verifyNoMoreInteractions(gymGateway, gymClassRepository);
    }

    @Test
    void shouldThrowInvalidInstructorIdException_whenInstructorIdIsInvalid(){
        var gymClassInput = GymClassFactory.buildCreateGymClassInput();
        var  instructorId = gymClassInput.instructorId();

        when(gymGateway.isValidInstructor(instructorId))
                .thenReturn(false);

        InvalidInstructorIdException exception = assertThrows(
                InvalidInstructorIdException.class,
                () -> createGymClassUseCase.createGymClass(gymClassInput)
        );

        assertEquals("Please provide a valid instructor id", exception.getMessage());

        verify(gymGateway).isValidInstructor(instructorId);
        verify(gymClassRepository, never()).save(any(GymClass.class));
        verifyNoMoreInteractions(gymGateway, gymClassRepository);
    }
}
