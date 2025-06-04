package de.sonar.hernebackend.datenmodell;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private User user;

    private String city;

    private String street;

    private String streetNumber;

    private String plz;

    private String district;

}
