package de.sonar.sonar.repositories;

import de.sonar.sonar.model.AdministrativeUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrativeUserRepository extends JpaRepository<AdministrativeUser, Long> {
}
