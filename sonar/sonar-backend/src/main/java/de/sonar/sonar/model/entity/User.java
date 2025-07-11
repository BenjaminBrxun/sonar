package de.sonar.sonar.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "sonar-user")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BasicUser {

    private String username;

    private Date birthDate;

    @OneToOne
    private InterestsProfile profile;

}