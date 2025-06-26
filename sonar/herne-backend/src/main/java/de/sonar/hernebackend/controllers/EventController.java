package de.sonar.hernebackend.controllers;

import de.sonar.hernebackend.model.entity.Event;
import de.sonar.hernebackend.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventRegistrationService eventRegistrationService;
    private final EventProcessionService eventProcessionService;
    private final EventCancellationService eventCancellationService;
    private final EventDeclineService eventDeclineService;
    private final EventDeletionService eventDeletionService;

    @PostMapping
    public Event registerNewEvent(@RequestBody Event newEvent) {
        return eventRegistrationService.registerNewEvent(newEvent);
    }

    @PutMapping
    public Event proceedEvent(@RequestBody Event event) {
        return eventProcessionService.proceedEvent(event);
    }

    @DeleteMapping
    public void deleteEvent(@RequestBody Event event) {
        eventDeletionService.deleteEvent(event);
    }

    @PostMapping("/decline")
    public Event declineEvent(@RequestBody Event event) {
        return eventDeclineService.declineEvent(event);
    }

    @PostMapping("/cancel")
    public Event cancelEvent(@RequestBody Event event) {
        return eventCancellationService.cancelEvent(event);
    }
}
