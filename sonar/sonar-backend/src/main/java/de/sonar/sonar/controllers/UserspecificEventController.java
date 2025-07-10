package de.sonar.sonar.controllers;

import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserspecificEventController {
    @Autowired
    private final UserService userService;


    public UserspecificEventController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/favourites")
    public List<Event> getFavourites(@RequestParam String email) {
        return userService.getFavouritesByEmail(email);
    }

    @PostMapping("/favourites")
    public void addFavourites(@RequestParam Long eventId, @RequestParam String email) {
        userService.addToFavouritesForEmail(email, eventId);
    }
}
