package de.sonar.sonar.services;

import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.model.entity.InterestsProfile;
import de.sonar.sonar.model.entity.User;
import de.sonar.sonar.repositories.EventRepository;
import de.sonar.sonar.repositories.InterestsProfileRepository;
import de.sonar.sonar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final EventRepository eventRepository;

    @Autowired
    private final InterestsProfileRepository interestsProfileRepository;

    public UserService(UserRepository userRepository, EventRepository eventRepository, InterestsProfileRepository interestsProfileRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.interestsProfileRepository = interestsProfileRepository;
    }

    public List<Event> getFavouritesByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user.getProfile().getFavorites();
    }

    public void addToFavouritesForEmail(String email, long eventId) {
        User user = userRepository.findByEmail(email);
        Event event = eventRepository.getOne(eventId);
        user.getProfile().getFavorites().add(event);
        userRepository.save(user);
    }

    public User registerUser(User user) {
        if(user.getProfile() == null) {
            InterestsProfile interestsProfile = InterestsProfile.builder().build();
            InterestsProfile saved = interestsProfileRepository.save(interestsProfile);
            user.setProfile(saved);
        }
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
