package de.sonar.hernebackend.controllers;

import de.sonar.hernebackend.model.entity.Event;
import de.sonar.hernebackend.services.*;
import io.swagger.v3.oas.annotations.Operation;
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


    @Operation(
            summary = "Register a new event.",
            description = """
                    Register a new event over MQTT in the sonar backend.
                    
                    If the event is already present in database or has an invalid state,
                    an exception is thrown.
                    """
    )
    @PostMapping
    public Event registerNewEvent(@RequestBody Event newEvent) {
        return eventRegistrationService.registerNewEvent(newEvent);
    }

    @Operation(
            summary = "Proceed the event lifecycle.",
            description = """
                    Proceed the event lifecycle for a given event.
                    
                    The event have to be already registered in database,
                    otherwise an exception is thrown.
                    
                    Valid state transitions:
                    
                
                    """
    )
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
