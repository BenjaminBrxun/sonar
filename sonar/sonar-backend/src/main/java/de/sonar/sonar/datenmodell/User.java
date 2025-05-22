package de.sonar.sonar.datenmodell;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class User extends BasicUser {

    private String username;

    private Date birthDate;

    @OneToOne
    private InterestsProfile profile;
}