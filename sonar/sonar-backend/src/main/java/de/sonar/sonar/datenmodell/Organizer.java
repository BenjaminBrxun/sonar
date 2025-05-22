package de.sonar.sonar.datenmodell;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name="organizer")
@NoArgsConstructor
@AllArgsConstructor

public class Organizer extends BasicUser {
    private String organisation;
}
