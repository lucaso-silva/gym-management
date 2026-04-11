package com.lucas.gym_management.user.application.usecase.impl;

import com.lucas.gym_management.user.application.domain.model.Student;
import com.lucas.gym_management.user.application.dto.user.UserOutput;
import com.lucas.gym_management.user.application.exceptions.ConflictException;
import com.lucas.gym_management.user.application.exceptions.NotAuthorizedException;
import com.lucas.gym_management.user.application.exceptions.NotFoundException;
import com.lucas.gym_management.user.application.ports.inbound.update.UpdateUserInput;
import com.lucas.gym_management.user.application.ports.outbound.repository.UserRepository;
import com.lucas.gym_management.user.factory.UserFactory;
import com.lucas.gym_management.user.application.usecase.impl.UpdateUserUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    @Test
    void shouldUpdateUser_whenDataIsValid(){
        var student = UserFactory.buildStudent();
        var manager = UserFactory.buildManager();
        var loggedInUserId = manager.getId();
        var userId = student.getId();
        var updateStudentInput = UserFactory.buildUpdateStudentInput();
        var updatedStudent = UserFactory.buildUpdatedStudent(student.getId());

        when(userRepository.findById(loggedInUserId))
                .thenReturn(Optional.of(manager));
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(student));
        when(userRepository.existsByEmailIdNot(updateStudentInput.email(), userId))
                .thenReturn(false);
        when(userRepository.updateUser(any(Student.class)))
                .thenReturn(updatedStudent);

        var output =  updateUserUseCase.updateUser(loggedInUserId, userId, updateStudentInput);

        assertNotNull(output);
        assertStudentOutputMatches(updatedStudent, output);

        verify(userRepository).findById(loggedInUserId);
        verify(userRepository).findById(userId);
        verify(userRepository).existsByEmailIdNot(updateStudentInput.email(), userId);
        verify(userRepository).updateUser(any(Student.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldUpdateUser_whenUserUpdatesHimself(){
        var student = UserFactory.buildStudent();
        var loggedInUserId = student.getId();
        var userId = student.getId();
        var updateStudentInput = UserFactory.buildUpdateStudentInput();
        var updatedStudent = UserFactory.buildUpdatedStudent(student.getId());

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(student));
        when(userRepository.existsByEmailIdNot(updateStudentInput.email(), userId))
                .thenReturn(false);
        when(userRepository.updateUser(any(Student.class)))
                .thenReturn(updatedStudent);

        var output = updateUserUseCase.updateUser(loggedInUserId, userId, updateStudentInput);

        assertNotNull(output);
        assertStudentOutputMatches(updatedStudent, output);

        verify(userRepository).findById(userId);
        verify(userRepository).existsByEmailIdNot(updateStudentInput.email(), userId);
        verify(userRepository).updateUser(any(Student.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowNotFoundException_whenLoggedInUserNotFound(){
        var loggedInUserId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var input = UserFactory.buildUpdateStudentInput();

        when(userRepository.findById(loggedInUserId))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> updateUserUseCase.updateUser(loggedInUserId, userId, input)
        );

        assertEquals("User with id %s not found".formatted(loggedInUserId), exception.getMessage());

        verify(userRepository).findById(loggedInUserId);
        verify(userRepository, never()).findById(userId);
        verify(userRepository, never()).existsByEmailIdNot(any(String.class), any(UUID.class));
        verify(userRepository, never()).updateUser(any(Student.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowNotFoundException_whenUserNotFound(){
        var manager = UserFactory.buildManager();
        var loggedInUserId = manager.getId();
        var userId = UUID.randomUUID();
        var updateStudentInput = UserFactory.buildUpdateStudentInput();

        when(userRepository.findById(loggedInUserId))
                .thenReturn(Optional.of(manager));
        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                ()-> updateUserUseCase.updateUser(loggedInUserId, userId, updateStudentInput)
        );

        assertEquals("User with id %s not found".formatted(userId), exception.getMessage());

        verify(userRepository).findById(loggedInUserId);
        verify(userRepository).findById(userId);
        verify(userRepository, never()).existsByEmailIdNot(any(String.class), any(UUID.class));
        verify(userRepository, never()).updateUser(any(Student.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowNotAuthorizedException_whenLoggedInUserIsNotManager(){
        var instructor = UserFactory.buildInstructor();
        var student = UserFactory.buildStudent();
        var loggedInUserId = instructor.getId();
        var userId = student.getId();
        var input = UserFactory.buildUpdateStudentInput();

        when(userRepository.findById(loggedInUserId))
                .thenReturn(Optional.of(instructor));

        NotAuthorizedException exception = assertThrows(
                NotAuthorizedException.class,
                () -> updateUserUseCase.updateUser(loggedInUserId, userId, input)
        );

        assertEquals("Logged in user is not allowed to perform update", exception.getMessage());

        verify(userRepository).findById(loggedInUserId);
        verify(userRepository, never()).findById(userId);
        verify(userRepository, never()).existsByEmailIdNot(any(String.class), any(UUID.class));
        verify(userRepository, never()).updateUser(any(Student.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowConflictException_whenEmailIsAlreadyUsed(){
        var manager = UserFactory.buildManager();
        var student = UserFactory.buildStudent();
        var loggedInUserId = manager.getId();
        var userId = student.getId();
        var input =  UserFactory.buildUpdateStudentInput();

        when(userRepository.findById(loggedInUserId))
                .thenReturn(Optional.of(manager));
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(student));
        when(userRepository.existsByEmailIdNot(input.email(), userId))
                .thenReturn(true);

        ConflictException exception = assertThrows(
                ConflictException.class,
                () -> updateUserUseCase.updateUser(loggedInUserId, userId, input)
        );

        assertEquals("Email %s already used".formatted(input.email()), exception.getMessage());

        verify(userRepository).findById(loggedInUserId);
        verify(userRepository).findById(userId);
        verify(userRepository).existsByEmailIdNot(input.email(), userId);
        verify(userRepository, never()).updateUser(any(Student.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldUpdateUserName_whenOnlyNameIsProvided(){
        var student = UserFactory.buildStudent();
        var loggedInUserId = student.getId();
        var userId = student.getId();
        var input = new UpdateUserInput("update-user-name",
                null, null, null, null, null, null, null, null);
        var updatedStudent = Student.restore(student.getId(),
                input.name(),
                student.getEmail(),
                student.getLogin(),
                student.getPassword(),
                student.getPhone(),
                student.getAddress(),
                student.getCreatedAt(),
                LocalDateTime.now(),
                student.getBirthDate(),
                student.isActiveMembership());

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(student));
        when(userRepository.updateUser(any(Student.class)))
                .thenReturn(updatedStudent);

        var output = updateUserUseCase.updateUser(loggedInUserId, userId, input);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(student.getId(), output.getId()),
                ()-> assertEquals(input.name(), output.getName()),
                ()-> assertEquals(student.getLogin(), output.getLogin()),
                ()-> assertEquals(student.getEmail(), output.getEmail()),
                ()-> assertEquals(student.getPhone(), output.getPhone()),
                ()-> assertEquals(student.getAddress().getStreet(), output.getAddress().street()),
                ()-> assertEquals(student.getAddress().getNumber(), output.getAddress().number()),
                ()-> assertEquals(student.getAddress().getNeighborhood(), output.getAddress().neighborhood()),
                ()-> assertEquals(student.getAddress().getZipCode(), output.getAddress().zipCode()),
                ()-> assertEquals(student.getAddress().getCity(), output.getAddress().city()),
                ()-> assertEquals(student.getAddress().getState(), output.getAddress().state())
        );

        verify(userRepository).findById(userId);
        verify(userRepository, never()).existsByEmailIdNot(any(String.class), any(UUID.class));
        verify(userRepository).updateUser(any(Student.class));
        verifyNoMoreInteractions(userRepository);
    }

    private void assertStudentOutputMatches(Student expectedOutput, UserOutput output){
        assertAll(
                ()-> assertEquals(expectedOutput.getId(), output.getId()),
                ()-> assertEquals(expectedOutput.getName(), output.getName()),
                ()-> assertEquals(expectedOutput.getLogin(), output.getLogin()),
                ()-> assertEquals(expectedOutput.getEmail(), output.getEmail()),
                ()-> assertEquals(expectedOutput.getPhone(), output.getPhone()),
                ()-> assertEquals(expectedOutput.getAddress().getStreet(), output.getAddress().street()),
                ()-> assertEquals(expectedOutput.getAddress().getNumber(), output.getAddress().number()),
                ()-> assertEquals(expectedOutput.getAddress().getNeighborhood(), output.getAddress().neighborhood()),
                ()-> assertEquals(expectedOutput.getAddress().getZipCode(), output.getAddress().zipCode()),
                ()-> assertEquals(expectedOutput.getAddress().getCity(), output.getAddress().city()),
                ()-> assertEquals(expectedOutput.getAddress().getState(), output.getAddress().state())
        );
    }

}
