package com.lucas.gym_management.gym.application.usecase.impl;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.dto.GymAddressDTO;
import com.lucas.gym_management.gym.application.dto.GymOutput;
import com.lucas.gym_management.gym.application.exceptions.GymNotFoundException;
import com.lucas.gym_management.gym.application.ports.inbound.update.UpdateGymInput;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateGymUseCaseTest {

    @Mock
    private GymRepository gymRepository;

    @InjectMocks
    private UpdateGymUseCaseImpl updateGymUseCase;

    @Test
    void shouldUpdateGym_whenDataIsValid(){
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var createdAt = gym.getCreatedAt();
        var userId = UUID.randomUUID();
        var updateGymInput = GymFactory.buildUpdateGymInput();
        var updatedGym = GymFactory.buildUpdatedGym(gymId, createdAt);

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));
        when(gymRepository.save(any(Gym.class)))
                .thenReturn(updatedGym);

        var output = updateGymUseCase.updateGym(userId,gymId,updateGymInput);

        assertNotNull(output);
        assertGymOutputMatches(updatedGym, output);

        verify(gymRepository).findById(gymId);
        verify(gymRepository).save(gym);
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldUpdateGymName_whenOnlyNameIsProvided(){
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var userId = UUID.randomUUID();
        var updateGymNameInput = new UpdateGymInput("Updated-gym-name", null,null);

        gym.renameTo("Updated-gym-name");

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));
        when(gymRepository.save(any(Gym.class)))
                .thenReturn(gym);

        var output = updateGymUseCase.updateGym(userId,gymId,updateGymNameInput);

        assertNotNull(output);
        assertGymOutputMatches(gym, output);

        verify(gymRepository).findById(gymId);
        verify(gymRepository).save(gym);
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldUpdateGymPhone_whenOnlyPhoneIsProvided(){
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var userId = UUID.randomUUID();
        var updateGymPhoneInput = new UpdateGymInput(null,"updated-phone-num",null);

        gym.updatePhone("updated-phone-num");

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));
        when(gymRepository.save(any(Gym.class)))
                .thenReturn(gym);

        var output = updateGymUseCase.updateGym(userId,gymId,updateGymPhoneInput);

        assertNotNull(output);
        assertGymOutputMatches(gym, output);

        verify(gymRepository).findById(gymId);
        verify(gymRepository).save(gym);
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldUpdateAddress_whenOnlyAddressIsProvided(){
        var gym = GymFactory.buildGym();
        var gymId = gym.getId();
        var userId = UUID.randomUUID();
        var updateGymAddressInput = new UpdateGymInput(null,null,
                new GymAddressDTO("updated-street-name",
                "updated-number",
                "updated-neighborhood",
                "updated-city",
                "updated-state"));

        gym.updatePhone("updated-phone-num");

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.of(gym));
        when(gymRepository.save(any(Gym.class)))
                .thenReturn(gym);

        var output = updateGymUseCase.updateGym(userId,gymId,updateGymAddressInput);

        assertNotNull(output);
        assertGymOutputMatches(gym, output);

        verify(gymRepository).findById(gymId);
        verify(gymRepository).save(gym);
        verifyNoMoreInteractions(gymRepository);
    }

    @Test
    void shouldThrowGymNotFoundException_whenGymIdIsNotValid(){
        var gymId = UUID.randomUUID();
        var userId = UUID.randomUUID();
        var updateGymInput = GymFactory.buildUpdateGymInput();

        when(gymRepository.findById(gymId))
                .thenReturn(Optional.empty());

        GymNotFoundException exception = assertThrows(
                GymNotFoundException.class,
                ()-> updateGymUseCase.updateGym(userId,gymId,updateGymInput)
        );

        assertEquals("Gym not found with id: %s".formatted(gymId), exception.getMessage());

        verify(gymRepository).findById(gymId);
        verify(gymRepository, never()).save(any(Gym.class));
        verifyNoMoreInteractions(gymRepository);
    }

    private void assertGymOutputMatches(Gym expectedOutput, GymOutput output){
        assertAll(
                ()-> assertEquals(expectedOutput.getId(), output.uuid()),
                ()-> assertEquals(expectedOutput.getName(), output.name()),
                ()-> assertEquals(expectedOutput.getPhone(), output.phone()),
                ()-> assertEquals(expectedOutput.getAddress().getStreet(), output.address().street()),
                ()-> assertEquals(expectedOutput.getAddress().getNumber(), output.address().number()),
                ()-> assertEquals(expectedOutput.getAddress().getNeighborhood(), output.address().neighborhood()),
                ()-> assertEquals(expectedOutput.getAddress().getCity(), output.address().city()),
                ()-> assertEquals(expectedOutput.getAddress().getState(), output.address().state()),
                ()-> assertEquals(expectedOutput.getMembersIds().size(), output.members()),
                ()-> assertEquals(expectedOutput.getGymClassesIds().size(), output.activeClasses())
        );
    }
}
