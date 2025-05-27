package de.sonar.sonar.repositories;

import de.sonar.sonar.datenmodell.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
