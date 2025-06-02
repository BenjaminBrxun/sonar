package de.sonar.sonar.datenmodell;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class AdministrativeUser {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

}
