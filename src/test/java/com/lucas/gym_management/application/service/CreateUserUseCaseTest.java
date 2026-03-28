package com.lucas.gym_management.application.service;

import com.lucas.gym_management.application.domain.command.CreateUserData;
import com.lucas.gym_management.application.domain.model.Manager;
import com.lucas.gym_management.application.domain.model.Student;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserOutput;
import com.lucas.gym_management.application.ports.outbound.repository.UserRepository;
import com.lucas.gym_management.factory.UserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Test
    void create_shouldCreateStudent_whenDataIsValid(){
        var studentInput = UserFactory.buildStudentInput();
        var student = UserFactory.buildStudent();

        when(userRepository.existsByEmail(studentInput.email()))
                .thenReturn(false);

        when(userRepository.existsByLogin(studentInput.login()))
                .thenReturn(false);

        when(userRepository.save(any(Student.class)))
                .thenReturn(student);

        CreateUserOutput output = createUserUseCase.createUser(studentInput);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals("any-name", output.name()),
                ()-> assertEquals("test@email.com", output.email()),
                ()-> assertEquals("any-login", output.login())
        );

        verify(userRepository, times(1)).existsByEmail(studentInput.email());
        verify(userRepository, times(1)).existsByLogin(studentInput.login());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void create_shouldCreateManager_whenDataIsValid(){
        var managerInput = UserFactory.buildManagerInput();
        var manager = UserFactory.buildManager();

        when(userRepository.existsByEmail(managerInput.email()))
                .thenReturn(false);

        when(userRepository.existsByLogin(managerInput.login()))
                .thenReturn(false);

        when(userRepository.save(any(Manager.class)))
                .thenReturn(manager);

        CreateUserOutput output = createUserUseCase.createUser(managerInput);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals("any-name", output.name()),
                ()-> assertEquals("any-login", output.login()),
                ()-> assertEquals("test@email.com", output.email())
        );

        verify(userRepository, times(1)).existsByEmail(managerInput.email());
        verify(userRepository, times(1)).existsByLogin(managerInput.login());
        verifyNoMoreInteractions(userRepository);
    }
}
