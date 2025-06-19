package de.sonar.sonar.model;

import de.sonar.sonar.model.state.EventState;
import de.sonar.sonar.model.state.EventStateConverter;
import de.sonar.sonar.model.state.NewEventState;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
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

    @Embedded
    private Address address;

    @ManyToMany
    private List<Category> categories;

    private Date startDate;

    private Date endDate;

    @OneToOne
    private Organizer applicant;

    @ManyToOne
    private AdministrativeUser processor;

    @Convert(converter = EventStateConverter.class)
    private EventState eventState = new NewEventState();

}
