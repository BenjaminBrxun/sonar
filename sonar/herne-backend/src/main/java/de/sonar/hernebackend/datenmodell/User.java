package de.sonar.hernebackend.datenmodell;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BasicUser {

    private String username;

    private Date birthDate;

    private InterestsProfile profile;
}