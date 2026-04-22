package com.lucas.gym_management.gym.factory;

import com.lucas.gym_management.gym.application.domain.model.Gym;
import com.lucas.gym_management.gym.application.domain.model.valueObjects.GymAddress;
import com.lucas.gym_management.gym.application.dto.GymAddressDTO;
import com.lucas.gym_management.gym.application.ports.inbound.create.CreateGymInput;

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
}
