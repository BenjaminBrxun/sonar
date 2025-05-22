package de.sonar.sonar.datenmodell;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;
    private String name;
    @Embedded
    private Address address;

    @ManyToMany
    private List<Category> categories;

    private Date start_date;
    private Date end_date;

    @OneToOne
    private Organizer applicant;

    @ManyToOne
    private AdministrativeUser processor;
    private String status;

}
