package com.lucas.gym_management.user.application.service;

import com.lucas.gym_management.user.application.domain.model.Student;
import com.lucas.gym_management.user.application.exceptions.ConflictException;
import com.lucas.gym_management.user.application.exceptions.NotAuthorizedException;
import com.lucas.gym_management.user.application.exceptions.NotFoundException;
import com.lucas.gym_management.user.application.ports.outbound.repository.UserRepository;
import com.lucas.gym_management.user.factory.UserFactory;
import com.lucas.gym_management.user.application.service.DeleteUserUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserUseCaseImpl deleteUserUseCase;

    @Test
    void shouldDeleteUser_whenLoggedInUserIsManagerAndUserExists() {
        var manager = UserFactory.buildManager();
        UUID loggedInUserId = manager.getId();
        UUID userId = UUID.randomUUID();
        var student = Student.restore(userId,
                "any-name",
                "any-email",
                "any-login",
                "any-pass",
                "any-phone",
                UserFactory.buildAddress(),
                LocalDateTime.now().minusMinutes(10),
                LocalDateTime.now().minusMinutes(1),
                LocalDate.now().minusYears(18),
                false);

        when(userRepository.findById(loggedInUserId))
                .thenReturn(Optional.of(manager));
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(student));

        deleteUserUseCase.deleteUserById(loggedInUserId, userId);

        verify(userRepository, times(2)).findById(any(UUID.class));
        verify(userRepository).deleteById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowNotFoundException_whenLoggedInUserNotFound(){
        var loggedInUserId = UUID.randomUUID();
        var userId = UUID.randomUUID();

        when(userRepository.findById(loggedInUserId))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> deleteUserUseCase.deleteUserById(loggedInUserId, userId)
        );

        assertEquals("User with id " + loggedInUserId + " not found", exception.getMessage());

        verify(userRepository).findById(loggedInUserId);
        verify(userRepository, never()).deleteById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowNotFoundException_whenUserNotFound(){
        var loggedInUserId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var manager = UserFactory.buildManager();

        when(userRepository.findById(loggedInUserId))
                .thenReturn(Optional.of(manager));
        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> deleteUserUseCase.deleteUserById(loggedInUserId, userId)
        );

        assertEquals("User with id " + userId + " not found", exception.getMessage());

        verify(userRepository, times(2)).findById(any(UUID.class));
        verify(userRepository, never()).deleteById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowNotAuthorizedException_whenLoggedInUserIsNotManager(){
        var instructor = UserFactory.buildInstructor();
        var student = UserFactory.buildStudent();
        var loggedInUserId = instructor.getId();
        var userId = student.getId();

        when(userRepository.findById(loggedInUserId))
                .thenReturn(Optional.of(instructor));
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(student));

        NotAuthorizedException exception = assertThrows(
                NotAuthorizedException.class,
                () -> deleteUserUseCase.deleteUserById(loggedInUserId, userId)
        );

        assertEquals("User cannot perform delete", exception.getMessage());

        verify(userRepository, times(2)).findById(any(UUID.class));
        verify(userRepository, never()).deleteById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowConflictException_whenStudentHasActiveMembership(){
        var manager = UserFactory.buildManager();
        var student = UserFactory.buildStudent();
        var loggedInUserId = manager.getId();
        var userId = student.getId();

        when(userRepository.findById(loggedInUserId))
                .thenReturn(Optional.of(manager));
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(student));

        ConflictException exception = assertThrows(
                ConflictException.class,
                () -> deleteUserUseCase.deleteUserById(loggedInUserId, userId)
        );

        assertEquals("Cannot delete a student with active membership, id " + userId, exception.getMessage());

        verify(userRepository, times(2)).findById(any(UUID.class));
        verify(userRepository, never()).deleteById(userId);
        verifyNoMoreInteractions(userRepository);
    }


}
