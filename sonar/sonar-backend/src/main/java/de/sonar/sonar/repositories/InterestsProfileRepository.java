package de.sonar.sonar.repositories;

import de.sonar.sonar.datenmodell.InterestsProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestsProfileRepository extends JpaRepository<InterestsProfile, Long> {
}
