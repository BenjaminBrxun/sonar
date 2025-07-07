package de.sonar.hernebackend.model.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestsProfile {

    private long id;

    private List<Category> interests;

    private List<Event> participatedEvents;

    private List<Event> favorites;

}
