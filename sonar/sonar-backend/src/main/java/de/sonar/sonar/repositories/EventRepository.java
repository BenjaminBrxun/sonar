package de.sonar.sonar.repositories;

import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.model.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    // Name
    List<Event> findAllByNameContainingIgnoreCase(String name);

    List<Event> findAllByStatusIn(Set<EventStatus> statuses);

    @Query("SELECT e FROM Event e WHERE e.status = 'DELETED'")
    List<Event> findAllDeletedEvents();

}