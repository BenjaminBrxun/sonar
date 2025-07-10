package de.sonar.hernebackend.model.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    private long id;

    private String name;

    private String description;

}
