package de.sonar.sonar.datenmodell;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BasicUser {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private long id;
    private String name;
    private String firstname;
    private String email;
    private String password;
    @Embedded
    private Address address;

}