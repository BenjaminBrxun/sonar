package de.sonar.sonar.repositories;

import de.sonar.sonar.datenmodell.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
