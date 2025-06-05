package de.sonar.sonar.services;

import de.sonar.sonar.datenmodell.Category;
import de.sonar.sonar.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.sonar.sonar.datenmodell.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAllByName(String name) {
        return eventRepository.findAllByNameContainingIgnoreCase(name);
    }

    /**
     * Schnittstelle für die Abfrage von Events mit entsprechenden Kategorien.
     *
     * @param categoryIds die IDs der Kategorien, für die die Events gesucht werden.
     * @return die Liste der Events, die die entsprechenden Kategorien haben.
     */
    public List<Event> findAllByCategories(List<String> categoryIds) {
        return eventRepository.findAllByCategories(categoryIds);
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> events(Long startDateInMilliseconds, Long endDateInMilliseconds) {
        Date startDate = new Date(startDateInMilliseconds);
        Date endDate;
        if (endDateInMilliseconds == null) {
            endDate = new Date(4102444799000l);
        } else {
            endDate = new Date(endDateInMilliseconds);
        }

        return eventRepository.findAllByStartDateBetween(startDate, endDate);
    }

    public List<Event> events(List<String> categories, Long startDateInMilliseconds, Long endDateInMilliseconds) {
        Date startDate = new Date(startDateInMilliseconds);
        Date endDate;
        if (endDateInMilliseconds == null) {
            endDate = new Date(4102444799000l);
        } else {
            endDate = new Date(endDateInMilliseconds);
        }
       return eventRepository.findAllByStartDateBetweenAndCategories(startDate, endDate, categories);
    }
}
