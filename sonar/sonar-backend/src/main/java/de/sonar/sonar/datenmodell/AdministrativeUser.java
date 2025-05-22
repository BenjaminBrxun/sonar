package de.sonar.sonar.datenmodell;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="administrativeUser")
public class AdministrativeUser {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private long id;

}
