package com.lucas.gym_management.application.service;

import com.lucas.gym_management.application.domain.model.Instructor;
import com.lucas.gym_management.application.domain.model.Manager;
import com.lucas.gym_management.application.domain.model.Student;
import com.lucas.gym_management.application.domain.model.UserType;
import com.lucas.gym_management.application.domain.model.exceptions.DomainException;
import com.lucas.gym_management.application.exceptions.ConflictException;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserInput;
import com.lucas.gym_management.application.ports.inbound.create.CreateUserOutput;
import com.lucas.gym_management.application.ports.outbound.repository.UserRepository;
import com.lucas.gym_management.factory.UserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Test
    void create_shouldCreateStudent_whenBirthDateIsProvided(){
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
                () -> assertEquals(student.getId(), output.id()),
                ()-> assertEquals("any-name", output.name()),
                ()-> assertEquals("test@email.com", output.email()),
                ()-> assertEquals("any-login", output.login())
        );

        verify(userRepository, times(1)).existsByEmail(studentInput.email());
        verify(userRepository, times(1)).existsByLogin(studentInput.login());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void create_shouldThrowDomainException_whenBirthDateLess16Years(){
        LocalDate invalidBirthDate = LocalDate.now().minusYears(15);
        var invalidStudentInput = new CreateUserInput(UserType.STUDENT,
                "any-name",
                "test@email.com",
                "any-login",
                "pass-123",
                "any-phone-number",
                UserFactory.buildAddressDTO(),
                null,
                null,
                null,
                invalidBirthDate);

        when(userRepository.existsByEmail(invalidStudentInput.email()))
                .thenReturn(false);

        when(userRepository.existsByLogin(invalidStudentInput.login()))
                .thenReturn(false);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> createUserUseCase.createUser(invalidStudentInput)
        );

        assertEquals("Student must be at least 16 years old", exception.getMessage());
    }

    @Test
    void create_shouldThrowDomainException_whenBirthDateIsInFuture(){
        LocalDate futureDate = LocalDate.now().plusDays(1);

        var studentInput = new CreateUserInput(UserType.STUDENT,
                "any-name",
                "test@email.com",
                "any-login",
                "pass-123",
                "any-phone-number",
                UserFactory.buildAddressDTO(),
                null,
                null,
                null,
                futureDate);

        when(userRepository.existsByEmail(studentInput.email()))
                .thenReturn(false);

        when(userRepository.existsByLogin(studentInput.login()))
                .thenReturn(false);

        DomainException exception =  assertThrows(
                DomainException.class,
                () -> createUserUseCase.createUser(studentInput)
        );

        assertEquals("Birth date cannot be in the future", exception.getMessage());

        verify(userRepository, times(1)).existsByEmail(studentInput.email());
        verify(userRepository, times(1)).existsByLogin(studentInput.login());
        verify(userRepository, never()).save(any(Student.class));
    }

    @Test
    void create_shouldCreateManager_whenGymNameIsProvided(){
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
                () -> assertEquals(manager.getId(), output.id()),
                ()-> assertEquals("any-name", output.name()),
                ()-> assertEquals("any-login", output.login()),
                ()-> assertEquals("test@email.com", output.email())
        );

        verify(userRepository, times(1)).existsByEmail(managerInput.email());
        verify(userRepository, times(1)).existsByLogin(managerInput.login());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void create_shouldCreateInstructor_whenCrefAndSpecialtyAreProvided(){
        var instructorInput = UserFactory.buildInstructorInput();
        var instructor = UserFactory.buildInstructor();

        when(userRepository.existsByEmail(instructorInput.email()))
                .thenReturn(false);
        when(userRepository.existsByLogin(instructorInput.login()))
                .thenReturn(false);
        when(userRepository.save(any(Instructor.class)))
                .thenReturn(instructor);

        var output = createUserUseCase.createUser(instructorInput);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(instructor.getId(), output.id()),
                ()-> assertEquals("any-name", output.name()),
                ()-> assertEquals("any-login", output.login()),
                ()-> assertEquals("test@email.com", output.email())
        );

        verify(userRepository, times(1)).existsByEmail(instructorInput.email());
        verify(userRepository, times(1)).existsByLogin(instructorInput.login());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void create_shouldThrowConflictException_whenEmailAlreadyExists(){
        var instructorInput = UserFactory.buildInstructorInput();

        when(userRepository.existsByEmail(instructorInput.email()))
                .thenReturn(true);

        ConflictException exception = assertThrows(
                ConflictException.class,
                () -> createUserUseCase.createUser(instructorInput)
        );

        assertEquals("Email test@email.com already used.", exception.getMessage());

        verify(userRepository, times(1)).existsByEmail(instructorInput.email());
        verify(userRepository, never()).existsByLogin(instructorInput.login());
        verify(userRepository, never()).save(any(Instructor.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void create_shouldThrowConflictException_whenLoginAlreadyExists(){
        var instructorInput = UserFactory.buildInstructorInput();

        when(userRepository.existsByEmail(instructorInput.email()))
                .thenReturn(false);
        when(userRepository.existsByLogin(instructorInput.login()))
                .thenReturn(true);

        ConflictException exception = assertThrows(
                ConflictException.class,
                () -> createUserUseCase.createUser(instructorInput)
        );

        assertEquals("Login any-login already used.", exception.getMessage());

        verify(userRepository, times(1)).existsByEmail(instructorInput.email());
        verify(userRepository, times(1)).existsByLogin(instructorInput.login());
        verify(userRepository, never()).save(any(Instructor.class));
        verifyNoMoreInteractions(userRepository);
    }

}
