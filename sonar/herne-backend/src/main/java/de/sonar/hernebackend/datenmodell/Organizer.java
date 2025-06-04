package de.sonar.hernebackend.datenmodell;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Organizer extends BasicUser {

    private String organisation;

}
