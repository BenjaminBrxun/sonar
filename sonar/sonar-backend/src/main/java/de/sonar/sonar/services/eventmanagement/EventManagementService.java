package de.sonar.sonar.services.eventmanagement;

import de.sonar.sonar.model.Event;
import de.sonar.sonar.model.state.InvalidEventStateException;
import de.sonar.sonar.model.state.NewEventState;
import de.sonar.sonar.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventManagementService {

    private final EventRepository eventRepository;

    // Event anlegen
    // Event reviewn
    // Event genehmigen
    // Event veröffentlichen
    // Event archivieren
    public void processEvent(Event event) {
        if (!(event.getEventState() instanceof NewEventState)) {
            Optional<Event> optionalEvent = eventRepository.findById(event.getId());
            if (optionalEvent.isEmpty()) {
                throw new InvalidEventStateException("Cannot process event, event is not new and does not exist in database");
            }
        }
        event.getEventState().handleEvent(event);
        eventRepository.save(event);
    }

    // Event löschen
    public void deleteEvent(Event event) {
        Optional<Event> optionalEvent = eventRepository.findById(event.getId());
        if (optionalEvent.isEmpty()) {
            throw new InvalidEventStateException("Cannot delete event, event does not exist in database");
        }
        event.getEventState().deleteEvent(event);
        eventRepository.save(event);
    }

}
