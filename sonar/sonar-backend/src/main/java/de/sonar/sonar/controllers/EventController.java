package de.sonar.sonar.controllers;

import de.sonar.sonar.datenmodell.Event;
import de.sonar.sonar.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/search/{name}")
    public List<Event> events(@PathVariable String name) {
        return eventService.findAllByName(name);
    }
/*
    @GetMapping("/filter/{categories}")
    public List<Event> events(@PathVariable List<String> categories) {
        return eventService.findAllByCategories(categories);
    }*/
    @GetMapping
    public String test() {
        return "test";
    }
    @PostMapping("/event")
    public Event createEvent(@RequestBody Event event) {
        return eventService.save(event);
    }
}