package de.sonar.hernebackend.model.entity;

import de.sonar.hernebackend.model.enums.EventStatus;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    private long id;

    private String name;

    private Address address;

    private List<Category> categories;

    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    private Organizer applicant;

    private AdministrativeUser processor;

    private EventStatus status = EventStatus.UNDER_EDITING;

}
