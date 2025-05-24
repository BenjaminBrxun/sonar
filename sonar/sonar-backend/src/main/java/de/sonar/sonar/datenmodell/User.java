package de.sonar.sonar.datenmodell;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "sonar-user")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BasicUser {

    private String username;

    private Date birthDate;

    @OneToOne
    private InterestsProfile profile;
}