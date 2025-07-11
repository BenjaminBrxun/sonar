package de.sonar.sonar.controllers;

import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserSpecificEventController {

    private final UserService userService;

    @Operation(
            summary = "Returns all favourite events for a user by its email.",
            tags = "Custom Event")
    @GetMapping("/favourites")
    public List<Event> getFavourites(@RequestParam String email) {
        return userService.getFavouritesByEmail(email);
    }

    @Operation(
            summary = "Add a new event to favourite events for a user by its email.",
            tags = "Custom Event")
    @PostMapping("/favourites")
    public void addFavourites(@RequestParam Long eventId, @RequestParam String email) {
        userService.addToFavouritesForEmail(email, eventId);
    }

}
