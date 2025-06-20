package de.sonar.sonar.repositories;

import de.sonar.sonar.model.entity.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
}
