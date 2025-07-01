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
            summary = "Registers a new event.",
            description = """
                    Registers a new event over MQTT in the sonar backend.
                    
                    If the event is already present in database or has an invalid state,
                    an exception is thrown.
                    """
    )
    @PostMapping
    public Event registerNewEvent(@RequestBody Event newEvent) {
        return eventRegistrationService.registerNewEvent(newEvent);
    }

    @Operation(
            summary = "Proceeds the event lifecycle.",
            description = """
                    Proceeds the event lifecycle for a given event.
                    
                    The event have to be already registered in database,
                    otherwise an exception is thrown.
                    
                    Valid state transitions:
                    UNDER_EDITING -> IN_REVIEW -> APPROVED -> DEPLOYED -> ARCHIVED -> UNDER_EDITING
                    
                    DECLINED -> UNDER_EDITING
                    CANCELLED -> ARCHIVED
                    DELETED -> UNDER_EDITING
                    """
    )
    @PutMapping
    public Event proceedEvent(@RequestBody Event event) {
        return eventProcessionService.proceedEvent(event);
    }

    @Operation(
            summary = "Deletes an event.",
            description = """
                    Deletes an event.
                    
                    You have 30 days to proceed an deleted event, otherwise it gets really deleted.
                    
                    Valid state transitions:
                    The deleted status can be set from any status.
                    """
    )
    @DeleteMapping
    public void deleteEvent(@RequestBody Event event) {
        eventDeletionService.deleteEvent(event);
    }

    @Operation(
            summary = "Declines an event in review.",
            description = """
                    Declines an event in review.
                    
                    Valid state transitions:
                    IN_REVIEW -> DECLINED
                    """
    )
    @PostMapping("/decline")
    public Event declineEvent(@RequestBody Event event) {
        return eventDeclineService.declineEvent(event);
    }

    @Operation(
            summary = "Cancels an deployed event.",
            description = """
                    Cancels an deployed event.
                    
                    Valid state transitions:
                    DEPLOYED -> CANCELLED
                    """
    )
    @PostMapping("/cancel")
    public Event cancelEvent(@RequestBody Event event) {
        return eventCancellationService.cancelEvent(event);
    }
}
