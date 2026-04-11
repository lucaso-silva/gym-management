package com.lucas.gym_management.user.application.usecase.impl;

import com.lucas.gym_management.user.application.ports.outbound.repository.UserRepository;
import com.lucas.gym_management.user.factory.UserFactory;
import com.lucas.gym_management.user.application.usecase.impl.ListUsersUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListUsersUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ListUsersUseCaseImpl listUsersUseCase;

    @Test
    void shouldReturnAListOfUsers_whenThereAreStudents() {
        var instructor = UserFactory.buildInstructor();
        var student = UserFactory.buildStudent();
        var inList = List.of(instructor, student);
        var filter = "";

        when(userRepository.findAll()).thenReturn(inList);

        var outList = listUsersUseCase.listUsers(filter);

        assertNotNull(outList);
        assertEquals(2, outList.size());

        assertAll(
                ()-> assertEquals(inList.getFirst().getId(), outList.getFirst().id()),
                ()-> assertEquals(inList.getFirst().getName(), outList.getFirst().name()),
                ()-> assertEquals(inList.getFirst().getEmail(), outList.getFirst().email()),
                ()-> assertEquals(inList.getLast().getId(), outList.getLast().id()),
                ()-> assertEquals(inList.getLast().getName(), outList.getLast().name()),
                ()-> assertEquals(inList.getLast().getEmail(), outList.getLast().email())
        );

        verify(userRepository).findAll();
        verify(userRepository, never()).findByNameLike(any(String.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldReturnEmptyList_whenThereAreNoUsers() {
        var filter = "";

        when(userRepository.findAll()).thenReturn(List.of());

        var outList = listUsersUseCase.listUsers(filter);

        assertNotNull(outList);
        assertTrue(outList.isEmpty());

        verify(userRepository).findAll();
        verify(userRepository, never()).findByNameLike(any(String.class));
        verifyNoMoreInteractions(userRepository);
    }
}
