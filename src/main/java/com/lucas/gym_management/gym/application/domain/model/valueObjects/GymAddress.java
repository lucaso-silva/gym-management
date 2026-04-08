package com.lucas.gym_management.gym.application.domain.model.valueObjects;

import lombok.Getter;

@Getter
public class GymAddress {
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;

    private GymAddress(String street, String number, String neighborhood, String city, String state) {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
    }

    public static GymAddress newAddress(String street, String number, String neighborhood, String city, String state) {
        return new GymAddress(street, number, neighborhood, city, state);
    }
}
