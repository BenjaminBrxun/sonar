package de.sonar.sonar.datenmodell;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    @OneToOne
    private User user;
    private String ort;
    private String street;
    private String houseNumber;
    private String plz;
    private String district;

}
