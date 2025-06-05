package de.sonar.sonar.services;

import de.sonar.sonar.datenmodell.Category;
import de.sonar.sonar.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.sonar.sonar.datenmodell.Event;

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
     * @param categoryIds die IDs der Kategorien, für die die Events gesucht werden.
     * @return die Liste der Events, die die entsprechenden Kategorien haben.
     */
    public List<Event> findAllByCategories(List<String> categoryIds) {
        return eventRepository.findAllByCategories(categoryIds);
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> events(Date startDate, Date endDate) {
        if (endDate == null) {
            endDate = new Date("2099-12-31T23:59:59.069+00:00");
        }
        return eventRepository.findAllByStartDateBetween(startDate, endDate);
    }
}
