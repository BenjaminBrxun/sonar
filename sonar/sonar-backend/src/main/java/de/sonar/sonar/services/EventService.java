package de.sonar.sonar.services;

import de.sonar.sonar.datenmodell.Event;
import de.sonar.sonar.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Ruft alle Events aus der Datenbank ab.
     *
     * @return alle Events.
     */
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Ruft für alle Events mit passendem Namen aus der Datenbank ab.
     *
     * @param name Begriff, der im Titel der Events enthalten ist
     * @return alle Events mit passendem Namen.
     */
    public List<Event> findAllByName(String name) {
        return eventRepository.findAllByNameContainingIgnoreCase(name);
    }

    /**
     * Ruft alle Events mit entsprechenden Kategorien aus der Datenbank ab.
     *
     * @param categoryIds die IDs der Kategorien, für die die Events gesucht werden.
     * @return die Liste der Events, die die entsprechenden Kategorien haben.
     */
    public List<Event> findAllByCategories(List<String> categoryIds) {
        return eventRepository.findAllByCategoriesIn(categoryIds);
    }

    /**
     * Ruft alle Events, die in dem angegebenen Zeitraum liegen aus der Datenbank ab.
     * Ist kein Enddatum angegeben, werden alle Events ab dem Startdatum angegeben.
     *
     * @param startDateInMilliseconds Startdatum in Millisekunden
     * @param endDateInMilliseconds   Enddatum in Millisekunden
     * @return die Liste der Events, die in den angegebenen Zeitraum passen.
     */
    public List<Event> findAllByDateBetween(Long startDateInMilliseconds, Long endDateInMilliseconds) {
        Date startDate = new Date(startDateInMilliseconds);
        Date endDate = (endDateInMilliseconds == null) ? new Date(4102444799000L) : new Date(endDateInMilliseconds);

        return eventRepository.findAllByStartDateBetween(startDate, endDate);
    }

    /**
     * Ruft alle Events, die den angegebenen Kategorien zugewiesen wurden und in dem angegebenen
     * Zeitraum liegen aus der Datenbank ab.
     * Ist kein Enddatum angegeben, werden alle Events ab dem Startdatum angegeben.
     *
     * @param categories              Kategorien, zu denen die Events zugeordnet sein sollen
     * @param startDateInMilliseconds Startdatum in Millisekunden
     * @param endDateInMilliseconds   Enddatum in Millisekunden
     * @return die Liste der Events, die in die Filterkriterien passen.
     */
    public List<Event> findAllByCategoriesAndDateBetween(List<String> categories, Long startDateInMilliseconds, Long endDateInMilliseconds) {
        Date startDate = (startDateInMilliseconds == null) ? null : new Date(startDateInMilliseconds);
        Date endDate = (endDateInMilliseconds == null) ? new Date(4102444799000L) : new Date(endDateInMilliseconds);

        return eventRepository.findAllByStartDateBetweenAndCategories(startDate, endDate, categories);
    }

    /**
     * Ruft alle Events, die den angegebenen Kategorien zugewiesen wurden und in dem angegebenen
     * Zeitraum liegen, sowie den angegebenen Begriff im Titel enthalten haben aus der Datenbank ab.
     * Ist kein Enddatum angegeben, werden alle Events ab dem Startdatum angegeben.
     *
     * @param categories              Kategorien, zu denen die Events zugeordnet sein sollen
     * @param name                    Begriff, der im Titel der Events enthalten ist
     * @param startDateInMilliseconds Startdatum in Millisekunden
     * @param endDateInMilliseconds   Enddatum in Millisekunden
     * @return die Liste der Events, die in die Filterkriterien passen.
     */
    public List<Event> findAllByCategoriesAndNameAndDateBetween(List<String> categories, String name, Long startDateInMilliseconds, Long endDateInMilliseconds) {
        Date startDate = (startDateInMilliseconds == null) ? null : new Date(startDateInMilliseconds);
        Date endDate = (endDateInMilliseconds == null) ? new Date(4102444799000L) : new Date(endDateInMilliseconds);

        return eventRepository.findAllByStartDateBetweenAndCategoriesAndNameContainingIgnoreCase(startDate, endDate, categories, name);
    }

    /**
     * Ruft alle Events, die den angegebenen Kategorien zugewiesen wurden und den angegebenen Begriff im Titel enthalten haben,
     * aus der Datenbank ab.
     *
     * @param name       Begriff, der im Titel enthalten sein soll
     * @param categories Kategorien, zu denen die Events zugeordnet sein solle.
     * @return die List der Events, die in die Filterkriterien passen.
     */
    public List<Event> findAllByNameContainingIgnoreCaseAndCategories(String name, List<String> categories) {
        return eventRepository.findAllByNameContainingIgnoreCaseAndCategories(name, categories);
    }

    /**
     * Ruft alle Events, die im angegebenen Zeitraum liegen und den angegebenen Begriff im Titel enthalten haben,
     * aus der Datenbank ab.
     *
     * @param startDateInMilliseconds Startdatum in Millisekunden
     * @param endDateInMilliseconds   Enddatum in Millisekunden
     * @param name      Begriff, der im Titel enthalten sein soll
     * @return die List der Events, die in die Filterkriterien passen.
     */
    public List<Event> findAllByStartDateBetweenAndNameContainingIgnoreCase(Long startDateInMilliseconds, Long endDateInMilliseconds, String name) {
        Date startDate = (startDateInMilliseconds == null) ? new Date() : new Date(startDateInMilliseconds);
        Date endDate = endDateInMilliseconds == null ? new Date(4102444799000L) : new Date(endDateInMilliseconds);

        return eventRepository.findAllByStartDateBetweenAndNameContainingIgnoreCase(startDate, endDate, name);
    }

}
