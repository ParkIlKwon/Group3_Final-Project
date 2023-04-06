package com.jpa.intra.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {

    private String address_name;
    private String road_address_name;
    protected Address(){}

    public Address(String address_name, String road_address_name) {
        this.address_name = address_name;
        this.road_address_name = road_address_name;
    }

}
