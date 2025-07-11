package de.sonar.sonar.controllers;

import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Statusschnittstelle.
     * Gibt an, dass das Backend verfügbar ist.
     *
     * @return den Verfügbarkeitsstatus
     */
    @Operation(
            summary = "Returns 'Backend verfügbar'",
            tags = "Metrics")
    @GetMapping
    public String getStatus() {
        return "Backend verfügbar.";
    }

    /**
     * Allgemeine Schnittstelle für alle Events
     *
     * @return alle Events
     */
    @Operation(
            summary = "Get all events.",
            description = "Get all events with state DEPLOYED and CANCELLED.",
            tags = "Event")
    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @Operation(
            summary = "Get an event by its ID.",
            tags = "Event")
    @GetMapping("/event/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    /**
     * Schnittstelle für die Suche von Events nach Titel.
     *
     * @param name Begriff, der im Titel enthalten ist.
     * @return alle Events mit passendem Titel.
     */
    @Operation(
            summary = "Search all events which name contains the given string.",
            description = "Search all events which name contains the given string and state DEPLOYED and CANCELLED.",
            tags = "Event")
    @GetMapping("/events/search")
    public List<Event> getAllEventsByName(@RequestParam String name) {
        return eventService.findAllByName(name);
    }


    /**
     * @param categories Kategorien, die den Events zugeordnet sind.
     * @param name       Begriff, der im Titel enthalten ist.
     * @param startDate  Startdatum in Millisekunden
     * @param endDate    Enddatum in Millisekunden
     * @param price      Die Kosten für den Eintritt zum Event
     * @param restricted wahr, wenn eine Anmeldung nötig ist
     * @return alle Events, die zum Filter passen.
     */
    @Operation(
            summary = "Get all events by filter.",
            description = """
                    Get all events by filter and state DEPLOYED and CANCELLED.
                    
                    You can filter for categories, name, startDate, endDate, price, restricted and minAge.
                    The filter will always only return events with state DEPLOYED and CANCELLED.
                    """,
            tags = "Event")
    @GetMapping("/events/filter")
    public List<Event> findAllByCategoriesAndNameAndDateBetween(
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) OffsetDateTime startDate,
            @RequestParam(required = false) OffsetDateTime endDate,
            @RequestParam(required = false) Float price,
            @RequestParam(required = false) Boolean restricted,
            @RequestParam(required = false) Integer minAge) {
        return eventService.findAllWithMatchingCriteria(
                categories, name, startDate, endDate, price, restricted, minAge);
    }

}