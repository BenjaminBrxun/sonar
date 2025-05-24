package de.sonar.sonar.services;

import de.sonar.sonar.datenmodell.Category;
import de.sonar.sonar.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.sonar.sonar.datenmodell.Event;

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

    public List<Event> findAllByCategories(List<String> categories) {
        return eventRepository.findAllByCategories_Name(categories);
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }
}
