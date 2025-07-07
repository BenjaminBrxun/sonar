package de.sonar.sonar.controllers;

import de.sonar.sonar.model.Event;
import de.sonar.sonar.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
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
    @GetMapping
    public String getStatus() {
        return "Backend verfügbar.";
    }

    /**
     * Allgemeine Schnittstelle für alle Events
     *
     * @return alle Events
     */
    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

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
    @GetMapping("/events/filter")
    public List<Event> findAllByCategoriesAndNameAndDateBetween(@RequestParam(required = false) List<Long> categories, @RequestParam(required = false) String name, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, @RequestParam(required = false) Float price, @RequestParam(required = false) Boolean restricted, @RequestParam(required = false) Integer minAge) {
        return eventService.findAllWithMatchingCriteria(categories, name, startDate, endDate, price, restricted, minAge);
    }

}