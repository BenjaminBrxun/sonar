package de.sonar.hernebackend.model.entity;

import de.sonar.hernebackend.model.enums.EventStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Schema(description = "Unique identifier of the event")
    private long id;

    @Schema(description = "Name of the event", example = "Sommerfest 2025")
    private String name;

    @Schema(description = "Physical address where the event takes place")
    private Address address;

    @Schema(description = "Categories this event belongs to")
    private List<Category> categories;

    @Schema(description = "Start date and time of the event", example = "2025-08-15T14:00:00+02:00")
    private OffsetDateTime startDate;

    @Schema(description = "End date and time of the event", example = "2025-08-15T22:00:00+02:00")
    private OffsetDateTime endDate;

    @Schema(description = "The organizer who created this event")
    private Organizer applicant;

    @Schema(description = "The administrative user processing this event")
    private AdministrativeUser processor;

    @Schema(
            description = "Current status of the event",
            example = "UNDER_EDITING",
            allowableValues = {"UNDER_EDITING", "IN_REVIEW", "APPROVED", "DEPLOYED", "ARCHIVED", "DECLINED", "CANCELLED", "DELETED"}
    )
    private EventStatus status = EventStatus.UNDER_EDITING;

}
