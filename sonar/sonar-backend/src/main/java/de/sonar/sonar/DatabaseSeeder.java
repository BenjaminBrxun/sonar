package de.sonar.sonar;

import de.sonar.sonar.model.*;
import de.sonar.sonar.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final OrganizerRepository organizerRepository;
    private final AdministrativeUserRepository administrativeUserRepository;
    private final UserRepository userRepository;
    private final InterestsProfileRepository interestsProfileRepository;

    @Autowired
    public DatabaseSeeder(EventRepository eventRepository, CategoryRepository categoryRepository, OrganizerRepository organizerRepository, AdministrativeUserRepository administrativeUserRepository, UserRepository userRepository, InterestsProfileRepository interestsProfileRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.organizerRepository = organizerRepository;
        this.administrativeUserRepository = administrativeUserRepository;
        this.userRepository = userRepository;
        this.interestsProfileRepository = interestsProfileRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Category category1 = Category.builder()
                .name("Sport")
                .description("Sportliche Aktivitäten")
                .build();
        Category category2 = Category.builder()
                .name("Familie")
                .description("Kinder und Eltern, sowie Freunde sind herzlich willkommen")
                .build();
        Category category3 = Category.builder()
                .name("Keine Ahnung")
                .description("Lalalalala")
                .build();

        List<Category> categoryList1 = new ArrayList<>();
        List<Category> categoryList2 = new ArrayList<>();
        List<Category> categoryList3 = new ArrayList<>();
        categoryList1.add(category1);
        categoryList2.add(category2);
        categoryList3.add(category3);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        Address address1 = new Address("Dortmund", "Hafenstraße", "12", "44227", "Eichlinghofen");
        Address address2 = new Address("Dortmund", "Feldweg", "122", "44229", "Oespel");

        Organizer applicant1 = Organizer.builder()
                .organisation("Stadt Dortmund")
                .build();
        Organizer applicant2 = Organizer.builder()
                .organisation("AWO Dortmund")
                .build();
        Organizer applicant3 = Organizer.builder()
                .organisation("AWO Herne")
                .build();
        Organizer applicant4 = Organizer.builder()
                .organisation("Tanz- und Turnclub Herne")
                .build();

        organizerRepository.save(applicant1);
        organizerRepository.save(applicant2);
        organizerRepository.save(applicant3);
        organizerRepository.save(applicant4);

        AdministrativeUser administrativeUser1 = AdministrativeUser.builder().build();
        AdministrativeUser administrativeUser2 = AdministrativeUser.builder().build();

        administrativeUserRepository.save(administrativeUser1);
        administrativeUserRepository.save(administrativeUser2);

        Event event1 = Event.builder()
                .name("Kleinfeld-Fußball Turnier")
                .address(address1)
                .categories(categoryList1)
                .startDate(new Date(1749119967000l))
                .endDate(new Date(1749148767000l))
                .applicant(applicant1)
                .processor(administrativeUser1)
                .status("offen")
                .build();
        Event event2 = Event.builder()
                .name("Familienfest im Stadtpark")
                .address(address2)
                .categories(categoryList2)
                .startDate(new Date(1749384000000l))
                .endDate(new Date(1749405600000l))
                .applicant(applicant2)
                .processor(administrativeUser2)
                .status("offen")
                .build();

        Event event3 = Event.builder()
                .name("Ferienlager")
                .address(address2)
                .categories(categoryList3)
                .startDate(new Date(1749384000000l))
                .endDate(new Date(1749751200000l))
                .applicant(applicant3)
                .processor(administrativeUser2)
                .status("offen")
                .build();

        Event event4 = Event.builder()
                .name("Großfeld-Fußball Turnier")
                .address(address1)
                .categories(categoryList1)
                .startDate(new Date(1749119967000l))
                .endDate(new Date(1749148767000l))
                .applicant(applicant4)
                .processor(administrativeUser1)
                .status("offen")
                .build();
        // Events speichern
        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);
        eventRepository.save(event4);

        List<Event> eventList1 = new ArrayList<>();
        eventList1.add(event1);

        List<Event> eventList2 = new ArrayList<>();
        eventList2.add(event2);

        InterestsProfile interestsProfile1 = InterestsProfile.builder()
                .interests(categoryList1)
                .participatedEvents(eventList1)
                .favorites(eventList1)
                .build();

        InterestsProfile interestsProfile2 = InterestsProfile.builder()
                .interests(categoryList2)
                .participatedEvents(eventList2)
                .favorites(eventList2)
                .build();

        interestsProfileRepository.save(interestsProfile1);
        interestsProfileRepository.save(interestsProfile2);

        User user1 = User.builder()
                .username("MichaDerHühne")
                .birthDate(new Date())
                .profile(interestsProfile1)
                .build();

        User user2 = User.builder()
                .username("FischmenschNils")
                .birthDate(new Date())
                .profile(interestsProfile2)
                .build();

        interestsProfileRepository.save(interestsProfile1);
        interestsProfileRepository.save(interestsProfile2);

        userRepository.save(user1);
        userRepository.save(user2);

        System.out.println("Datenbank wurde befüllt.");
    }
}