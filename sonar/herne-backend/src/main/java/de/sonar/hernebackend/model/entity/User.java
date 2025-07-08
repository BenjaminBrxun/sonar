package de.sonar.hernebackend.model.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BasicUser {

    private String username;

    private Date birthDate;

    private InterestsProfile profile;

}