package de.sonar.sonar.repositories;

import de.sonar.sonar.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    // Name
    List<Event> findAllByNameContainingIgnoreCase(String name);

    Event getEventById(Long id);
}