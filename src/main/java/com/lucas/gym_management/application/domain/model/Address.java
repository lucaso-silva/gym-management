package com.lucas.gym_management.application.domain.model;

import lombok.Getter;

@Getter
public class Address {
    private String street;
    private int number;
    private String neighborhood;
    private String zipCode;
    private String city;
    private String state;

    private Address(String street, int number, String neighborhood, String zipCode, String city, String state) {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
    }

    public static Address newAddress(String street, int number, String neighborhood, String zipCode, String city, String state) {
        return new Address(street, number, neighborhood, zipCode, city, state);
    }
}
