package de.sonar.sonar.services;

import de.sonar.sonar.dao.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event findByName(String name) {
        return eventRepository.findByName(name);
    }
}
