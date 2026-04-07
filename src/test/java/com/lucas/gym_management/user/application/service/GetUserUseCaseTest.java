package com.lucas.gym_management.user.application.service;

import com.lucas.gym_management.user.application.exceptions.NotFoundException;
import com.lucas.gym_management.user.application.ports.outbound.repository.UserRepository;
import com.lucas.gym_management.user.factory.UserFactory;
import com.lucas.gym_management.user.application.service.GetUserUseCaseImpl;
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
class GetUserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserUseCaseImpl getUserUseCase;

    @Test
    void shouldReturnUserById_whenUserExists(){
        var student = UserFactory.buildStudent();
        UUID userId = student.getId();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(student));

        var output = getUserUseCase.getUserById(userId);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(userId, output.getId()),
                ()-> assertEquals(student.getName(), output.getName()),
                ()-> assertEquals(student.getEmail(), output.getEmail()),
                ()-> assertEquals(student.getLogin(), output.getLogin()),
                ()-> assertEquals(student.getPhone(), output.getPhone()),
                ()-> assertEquals(student.getAddress().getStreet(), output.getAddress().street()),
                ()-> assertEquals(student.getAddress().getNumber(), output.getAddress().number()),
                ()-> assertEquals(student.getAddress().getNeighborhood(), output.getAddress().neighborhood()),
                ()-> assertEquals(student.getAddress().getCity(), output.getAddress().city()),
                ()-> assertEquals(student.getAddress().getState(), output.getAddress().state())
        );

        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowNotFoundException_whenUserDoesNotExist(){
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> getUserUseCase.getUserById(userId)
        );

        assertEquals("User with id " + userId + " not found", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldReturnUserByLogin_whenLoginExists() {
        var student = UserFactory.buildStudent();
        UUID userId = student.getId();
        var login = "any-login";

        when(userRepository.findByLogin(login))
                .thenReturn(Optional.of(student));

        var output = getUserUseCase.getUserByLogin(login);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(userId, output.getId()),
                ()-> assertEquals(student.getName(), output.getName()),
                ()-> assertEquals(student.getEmail(), output.getEmail()),
                ()-> assertEquals(student.getLogin(), output.getLogin()),
                ()-> assertEquals(student.getPhone(), output.getPhone()),
                ()-> assertEquals(student.getAddress().getStreet(), output.getAddress().street()),
                ()-> assertEquals(student.getAddress().getNumber(), output.getAddress().number()),
                ()-> assertEquals(student.getAddress().getNeighborhood(), output.getAddress().neighborhood()),
                ()-> assertEquals(student.getAddress().getCity(), output.getAddress().city()),
                ()-> assertEquals(student.getAddress().getState(), output.getAddress().state())
        );

        verify(userRepository, times(1)).findByLogin(login);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowNotFoundException_whenLoginDoesNotExist() {
        var invalidLogin = "invalid-login";

        when(userRepository.findByLogin(invalidLogin))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> getUserUseCase.getUserByLogin(invalidLogin)
        );

        assertEquals("User with login " + invalidLogin + " not found", exception.getMessage());

        verify(userRepository, times(1)).findByLogin(invalidLogin);
        verifyNoMoreInteractions(userRepository);
    }
}
