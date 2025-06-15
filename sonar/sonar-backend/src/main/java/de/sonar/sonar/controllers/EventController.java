package de.sonar.sonar.controllers;

import de.sonar.sonar.model.Event;
import de.sonar.sonar.services.EventService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
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
    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    /**
     * Schnittstelle für die Suche von Events nach Titel.
     *
     * @param name Begriff, der im Titel enthalten ist.
     * @return alle Events mit passendem Titel.
     */
    @GetMapping("/search")
    public List<Event> getAllEventsByName(@RequestParam String name) {
        return eventService.findAllByName(name);
    }

    /**
     * Schnittstelle fürs Filtern von Events nach Kategorien.
     *
     * @param categories Kategorien, die den Events zugeordnet sind.
     * @return alle Events, die zum Filter passen.
     */
    @GetMapping("/filter/categories")
    public List<Event> getAllEventsByCategories(@RequestParam List<String> categories) {
        return eventService.findAllByCategories(categories);
    }

    /**
     * Schnittstelle fürs Filtern von Events nach Datum.
     * Ist kein Enddatum angegeben, werden alle Events angezeigt, die zum Startdatum beginnen.
     *
     * @param startDate Startdatum in Millisekunden
     * @param endDate   Enddatum in Millisekunden
     * @return alle Events, die zum Filter passen.
     */
    @GetMapping("/filter/date")
    public List<Event> getAllEventsByDateBetween(@RequestParam Long startDate, @RequestParam @Nullable Long endDate) {
        return eventService.findAllByDateBetween(startDate, endDate);
    }

    @GetMapping("/filter/categories_name")
    public List<Event> getAllEventsByNameAndCategories(@RequestParam String name, @RequestParam List<String> categories) {
        return eventService.findAllByNameContainingIgnoreCaseAndCategories(name, categories);
    }

    @GetMapping("/filter/date_name")
    public List<Event> getAllEventsByDateBetweenAndName(@RequestParam Long startDate, @RequestParam Long endDate, @RequestParam String name) {
        return eventService.findAllByStartDateBetweenAndNameContainingIgnoreCase(startDate, endDate, name);
    }

    /**
     * Schnittstelle fürs Filter von Events nach Kategorien und Datum.
     * Ist kein Enddatum angegeben, werden alle Events angezeigt, die zum Startdatum beginnen.
     *
     * @param categories Kategorien, die den Events zugeordnet sind.
     * @param startDate  Startdatum in Millisekunden
     * @param endDate    Enddatum in Millisekunden
     * @return alle Events, die zum Filter passen.
     */
    @GetMapping("/filter/categories_date")
    public List<Event> getAllEventsByCategoriesAndDateBetween(@RequestParam List<String> categories, @RequestParam Long startDate, @RequestParam @Nullable Long endDate) {
        return eventService.findAllByCategoriesAndDateBetween(categories, startDate, endDate);
    }

    /**
     * @param categories Kategorien, die den Events zugeordnet sind.
     * @param name       Begriff, der im Titel enthalten ist.
     * @param startDate  Startdatum in Millisekunden
     * @param endDate    Enddatum in Millisekunden
     * @return alle Events, die zum Filter passen.
     */
    @GetMapping("/filter/full")
    public List<Event> findAllByCategoriesAndNameAndDateBetween(@RequestParam List<String> categories, @RequestParam String name, @RequestParam Long startDate, @RequestParam Long endDate) {
        return eventService.findAllByCategoriesAndNameAndDateBetween(categories, name, startDate, endDate);
    }
}