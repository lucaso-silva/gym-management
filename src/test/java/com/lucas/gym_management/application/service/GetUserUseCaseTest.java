package com.lucas.gym_management.application.service;

import com.lucas.gym_management.application.exceptions.NotFoundException;
import com.lucas.gym_management.application.ports.outbound.repository.UserRepository;
import com.lucas.gym_management.factory.UserFactory;
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
                ()-> assertEquals(userId, output.id()),
                ()-> assertEquals(student.getName(), output.name()),
                ()-> assertEquals(student.getEmail(), output.email()),
                ()-> assertEquals(student.getLogin(), output.login()),
                ()-> assertEquals(student.getPhone(), output.phone()),
                ()-> assertEquals(student.getAddress().getStreet(), output.address().street()),
                ()-> assertEquals(student.getAddress().getNumber(), output.address().number()),
                ()-> assertEquals(student.getAddress().getNeighborhood(), output.address().neighborhood()),
                ()-> assertEquals(student.getAddress().getCity(), output.address().city()),
                ()-> assertEquals(student.getAddress().getState(), output.address().state())
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
                ()-> assertEquals(userId, output.id()),
                ()-> assertEquals(student.getName(), output.name()),
                ()-> assertEquals(student.getEmail(), output.email()),
                ()-> assertEquals(student.getLogin(), output.login()),
                ()-> assertEquals(student.getPhone(), output.phone()),
                ()-> assertEquals(student.getAddress().getStreet(), output.address().street()),
                ()-> assertEquals(student.getAddress().getNumber(), output.address().number()),
                ()-> assertEquals(student.getAddress().getNeighborhood(), output.address().neighborhood()),
                ()-> assertEquals(student.getAddress().getCity(), output.address().city()),
                ()-> assertEquals(student.getAddress().getState(), output.address().state())
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
