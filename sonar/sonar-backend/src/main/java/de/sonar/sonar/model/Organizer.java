package de.sonar.sonar.model;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organizer extends BasicUser {

    private String organisation;

}
