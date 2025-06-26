package de.sonar.hernebackend.model.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private String city;

    private String street;

    private String houseNumber;

    private String postcode;

    private String district;

}
