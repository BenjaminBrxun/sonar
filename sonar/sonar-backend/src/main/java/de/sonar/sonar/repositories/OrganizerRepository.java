package de.sonar.sonar.repositories;

import de.sonar.sonar.datenmodell.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
}
