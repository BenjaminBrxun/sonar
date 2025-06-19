package de.sonar.sonar.repositories;

import de.sonar.sonar.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Name
    List<Event> findAllByNameContainingIgnoreCase(String name);

    // Kategorien
    List<Event> findAllByCategoriesIn(List<String> categories);

    // Datum
    List<Event> findAllByStartDateBetween(Date startDate, Date endDate);

    // Datum + Kategorien
    List<Event> findAllByStartDateBetweenAndCategories(Date startDate, Date endDate, List<String> categories);

    // Datum + Name
    List<Event> findAllByStartDateBetweenAndNameContainingIgnoreCase(Date startDate, Date endDate, String name);

    // Name + Kategorien
    List<Event> findAllByNameContainingIgnoreCaseAndCategories(String name, List<String> categories);

    // Datum + Kategorien + Name
    List<Event> findAllByStartDateBetweenAndCategoriesAndNameContainingIgnoreCase(Date startDate, Date endDate, List<String> categories, String name);

    @Query("SELECT e FROM Event e WHERE e.eventState = :#{#type.getSimpleName()}")
    List<Event> findAllByEventStateType(Class<?> type);


}