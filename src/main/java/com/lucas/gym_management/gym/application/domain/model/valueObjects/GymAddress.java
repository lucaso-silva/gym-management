package com.lucas.gym_management.gym.application.domain.model.valueObjects;

import com.lucas.gym_management.gym.application.domain.model.exceptions.DomainException;
import lombok.Getter;

@Getter
public class GymAddress {
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;

    protected GymAddress(String street, String number, String neighborhood, String city, String state) {
        if(street == null || street.isBlank() ||
                number == null || number.isBlank() ||
                neighborhood == null || neighborhood.isBlank() ||
                city == null || city.isBlank() ||
                state == null || state.isBlank()) {
            throw new DomainException("You must provide street, number, neighborhood, city and state");
        }
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
    }

    public static GymAddress newAddress(String street, String number, String neighborhood, String city, String state) {
        return new GymAddress(street, number, neighborhood, city, state);
    }

    public void updateStreet(String street) {
        if(street == null || street.isBlank()) {
            throw new DomainException("Street address cannot be     empty");
        }
        this.street = street;
    }

    public void updateNumber(String number) {
        if(number == null || number.isBlank()) {
            throw new DomainException("Number address cannot be empty");
        }
        this.number = number;
    }

    public void updateNeighborhood(String neighborhood) {
        if(neighborhood == null || neighborhood.isBlank()) {
            throw new DomainException("Neighborhood address cannot be empty");
        }
        this.neighborhood = neighborhood;
    }

    public void updateCity(String city) {
        if(city == null || city.isBlank()) {
            throw new DomainException("City address cannot be empty");
        }
        this.city = city;
    }

    public void updateState(String state) {
        if(state == null || state.isBlank()) {
            throw new DomainException("State address cannot be empty");
        }
        this.state = state;
    }
}
