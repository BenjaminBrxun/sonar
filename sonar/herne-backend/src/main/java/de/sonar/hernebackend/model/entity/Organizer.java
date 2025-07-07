package de.sonar.hernebackend.model.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organizer extends BasicUser {

    private String organisation;

}
