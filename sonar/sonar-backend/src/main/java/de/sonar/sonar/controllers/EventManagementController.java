package de.sonar.sonar.controllers;

import de.sonar.sonar.model.Event;
import de.sonar.sonar.services.eventmanagement.EventManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventManagementController {

    private final EventManagementService eventManagementService;

    @PostMapping
    public Event processEvent(Event event) {
        eventManagementService.processEvent(event);
        return event;
    }

    @DeleteMapping
    public void deleteEvent(Event event) {
        eventManagementService.deleteEvent(event);
    }

}
