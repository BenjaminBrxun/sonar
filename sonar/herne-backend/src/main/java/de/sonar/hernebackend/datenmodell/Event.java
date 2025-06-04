package de.sonar.hernebackend.datenmodell;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private long id;

    private String name;

    private Address address;

    private List<Category> categories;

    private Date startDate;

    private Date endDate;

    private Organizer applicant;

    private AdministrativeUser processor;

    private String status;

}
