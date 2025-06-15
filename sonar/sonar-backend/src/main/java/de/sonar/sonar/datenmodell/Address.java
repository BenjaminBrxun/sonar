package de.sonar.sonar.datenmodell;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class Address {

    private String city;

    private String street;

    private String houseNumber;

    private String postcode;

    private String district;

}
