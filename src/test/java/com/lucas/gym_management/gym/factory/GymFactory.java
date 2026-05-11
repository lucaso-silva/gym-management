package com.lucas.gym_management.gym.factory;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.domain.model.valueObjects.GymAddress;
import com.lucas.gym_management.gym.application.dto.GymAddressDTO;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymInput;
import com.lucas.gym_management.gym.application.ports.inbound.update.UpdateGymInput;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class GymFactory {
    public static CreateGymInput buildCreateGymInput() {
        return new CreateGymInput(
                "Any-gym-name",
                "any-phone-number",
                new GymAddressDTO("Any-street-name",
                        "any-number",
                        "any-neighborhood",
                        "any-city",
                        "any-state"
                ));
    }

    public static Gym buildGym() {
        return Gym.createNewGym("Any-gym-name",
                "any-phone-number",
                GymAddress.newAddress("Any-street-name",
                        "any-number",
                        "any-neighborhood",
                        "any-city",
                        "any-state"));
    }

    public static List<Gym> buildListOfGyms() {
        var gym1 = Gym.createNewGym("First-gym-name",
                "first-phone-number",
                GymAddress.newAddress("First-gym-street-name",
                        "any-number",
                        "any-neighborhood",
                        "any-city",
                        "any-state"));

        var gym2 = Gym.createNewGym("Second-gym-name",
                "second-phone-number",
                GymAddress.newAddress("Second-gym-street-name",
                        "another-number",
                        "another-neighborhood",
                        "another-city",
                        "another-state"));

        return List.of(gym1, gym2);
    }

    public static UpdateGymInput buildUpdateGymInput() {
        return new UpdateGymInput("Updated-gym-name",
                "updated-phone",
                new GymAddressDTO("updated-street-name",
                        "updated-number",
                        "updated-neighborhood",
                        "updated-city",
                        "updated-state"));
    }

    public static Gym buildUpdatedGym(UUID gymId, LocalDateTime createdAt) {
        return Gym.restoreGym(gymId,
                "Updated-gym-name",
                "updated-phone",
                new HashSet<>(),
                new HashSet<>(),
                GymAddress.newAddress("updated-street-name",
                        "updated-number",
                        "updated-neighborhood",
                        "updated-city",
                        "updated-state"),
                createdAt,
                LocalDateTime.now()
                );
    }
}
