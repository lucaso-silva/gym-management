package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.domain.model.exceptions.DomainException;
import com.lucas.gym_management.gym.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gym.application.ports.inbound.manage_members.AddMemberInput;
import com.lucas.gym_management.gym.application.ports.outbound.repository.GymRepository;
import com.lucas.gym_management.gym.factory.GymFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddMemberUseCaseTest {

    @Mock
    private GymRepository gymRepository;

    @InjectMocks
    private AddMemberUseCaseImpl addMemberUseCase;

    @Test
    void shouldAddAMemberToGym_whenDataIsValid() {
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var loggedInUserId = UUID.randomUUID();
        var memberId = UUID.randomUUID();
        var addMemberInput = new AddMemberInput(memberId);

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));
        when(gymRepository.save(gym))
                .thenReturn(gym);

        assertTrue(gym.getMembersIds().isEmpty());

        var output = addMemberUseCase.addMember(loggedInUserId, gymId, addMemberInput);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(gym.getId(), output.uuid()),
                ()-> assertEquals(gym.getName(), output.name()),
                ()-> assertEquals(gym.getPhone(), output.phone()),
                ()-> assertEquals(1, output.members()),
                () -> assertTrue(gym.getMembersIds().contains(memberId)),
                ()-> assertEquals(gym.getGymClassesIds().size(), output.activeClasses()),
                ()-> assertEquals(0, output.activeClasses())
        );

        verify(gymRepository).findById(gymId);
        verify(gymRepository).save(any(Gym.class));
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldThrowGymNotFoundException_WhenGymIdIsNotValid() {
        var gymId = UUID.randomUUID();
        var loggedInUserId = UUID.randomUUID();
        var addMemberInput = new AddMemberInput(UUID.randomUUID());

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.empty());

        GymNotFoundException exception = assertThrows(
                GymNotFoundException.class,
                () -> addMemberUseCase.addMember(loggedInUserId, gymId, addMemberInput)
                );

        assertEquals("Gym not found with id: %s".formatted(gymId),  exception.getMessage());

        verify(gymRepository).findById(gymId);
        verify(gymRepository, never()).save(any(Gym.class));
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldThrowDomainException_whenAddingDuplicateMember() {
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var userId = UUID.randomUUID();
        var memberId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        var addMemberInput = new AddMemberInput(memberId);

        gym.addMember(memberId);

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));

        DomainException exception = assertThrows(
                DomainException.class,
                ()-> addMemberUseCase.addMember(userId, gymId, addMemberInput)
        );

        assertEquals("User is already a gym member", exception.getMessage());

        verify(gymRepository).findById(gymId);
        verify(gymRepository, never()).save(any(Gym.class));
        verifyNoMoreInteractions(gymRepository);
    }
}
