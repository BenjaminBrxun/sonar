package de.sonar.sonar.repositories;

import de.sonar.sonar.datenmodell.Address;
import de.sonar.sonar.datenmodell.Category;
import de.sonar.sonar.datenmodell.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByNameContainingIgnoreCase(String name);

    //List<Event> findAllByCategories_Name(List<String> categories);

    Event save(Event event); // Müsste hier nicht stehen, macht SpringBoot automatisch!

}