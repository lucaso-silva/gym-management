package com.lucas.gym_management.user.application.domain.model.valueObjects;

import com.lucas.gym_management.user.application.domain.model.exceptions.RequiredFieldException;
import lombok.Getter;

@Getter
public class Address {
    private String street;
    private String number;
    private String neighborhood;
    private String zipCode;
    private String city;
    private String state;

    private Address(String street, String number, String neighborhood, String zipCode, String city, String state) {
        if (street == null || street.isBlank() ||
                number == null || number.isBlank() ||
                neighborhood == null || neighborhood.isBlank() ||
                zipCode == null || zipCode.isBlank() ||
                city == null || city.isBlank() ||
                state == null || state.isBlank()) {
            throw new RequiredFieldException("You must inform street, number, neighborhood, zipcode, city and state in the address.");
        }
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
    }

    public static Address newAddress(String street, String number, String neighborhood, String zipCode, String city, String state) {
        return new Address(street, number, neighborhood, zipCode, city, state);
    }
}
