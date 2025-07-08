package de.sonar.sonar.model.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
