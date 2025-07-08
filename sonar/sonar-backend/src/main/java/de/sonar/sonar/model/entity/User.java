package de.sonar.sonar.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "sonar-user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BasicUser {

    private String username;

    private Date birthDate;

    @OneToOne
    private InterestsProfile profile;

}