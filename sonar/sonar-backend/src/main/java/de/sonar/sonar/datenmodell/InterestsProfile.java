package de.sonar.sonar.datenmodell;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class InterestsProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToMany
    private List<Category> interests;

    @ManyToMany
    private List<Event> participatedEvents;

    @ManyToMany
    private List<Event> favorites;

}
