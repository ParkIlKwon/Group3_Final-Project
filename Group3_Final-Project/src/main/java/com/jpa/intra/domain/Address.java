package com.jpa.intra.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String address_name;
    private String road_address_name;
    protected Address(){}

    public Address(String address_name, String road_address_name) {
        this.address_name = address_name;
        this.road_address_name = road_address_name;
    }

}
