package de.sonar.hernebackend.datenmodell;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterestsProfile {

    private long id;

    private List<Category> interests;

    private List<Event> participatedEvents;

    private List<Event> favorites;

}
