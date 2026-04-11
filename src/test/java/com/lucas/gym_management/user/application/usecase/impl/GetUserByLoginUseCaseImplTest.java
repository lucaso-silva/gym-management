package com.lucas.gym_management.user.application.usecase.impl;

import com.lucas.gym_management.user.application.exceptions.NotFoundException;
import com.lucas.gym_management.user.application.ports.outbound.repository.UserRepository;
import com.lucas.gym_management.user.factory.UserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class GetUserByLoginUseCaseImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserByLoginUseCaseImpl getUserByLoginUseCase;

    @Test
    void shouldReturnUserByLogin_whenLoginExists() {
        var student = UserFactory.buildStudent();
        UUID userId = student.getId();
        var login = "any-login";

        when(userRepository.findByLogin(login))
                .thenReturn(Optional.of(student));

        var output = getUserByLoginUseCase.getUserByLogin(login);

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
                () -> getUserByLoginUseCase.getUserByLogin(invalidLogin)
        );

        assertEquals("User with login " + invalidLogin + " not found", exception.getMessage());

        verify(userRepository, times(1)).findByLogin(invalidLogin);
        verifyNoMoreInteractions(userRepository);
    }
}
