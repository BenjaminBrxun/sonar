package de.sonar.sonar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.sonar.sonar.model.enums.EventStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    private String headline;

    @Embedded
    private Address address;

    @ManyToMany
    private List<Category> categories;

    @Column(nullable = false)
    private OffsetDateTime startDate;

    @Column(nullable = false)
    private OffsetDateTime endDate;

    @ManyToOne
    private Organizer applicant;

    @ManyToOne
    private AdministrativeUser processor;

    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.UNDER_EDITING;

    @JsonIgnore
    private OffsetDateTime deletedStateAt;

    private float price;

    private int minAge;

    private boolean restricted;

    public boolean isNew() {
        return this.id <= 0;
    }

}
