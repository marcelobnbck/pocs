package org.example;

import com.google.gson.annotations.Expose;

// Nested object: Address
class Address {
    @Expose
    private String city;
    @Expose
    private String country;

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return city + ", " + country;
    }
}
