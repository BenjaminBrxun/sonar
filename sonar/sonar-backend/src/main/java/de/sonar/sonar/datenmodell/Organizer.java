package de.sonar.sonar.datenmodell;

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
