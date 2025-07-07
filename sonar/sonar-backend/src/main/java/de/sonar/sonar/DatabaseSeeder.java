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
                .name("Museum")
                .description("Kinder und Eltern, sowie Freunde sind herzlich willkommen")
                .build();
        Category category3 = Category.builder()
                .name("Musik")
                .description("Lalalalala")
                .build();
        Category category4 = Category.builder()
                .name("Fest")
                .description("Gute Stimmung für die ganze Familie!")
                .build();
        Category category5 = Category.builder()
                .name("Gaming")
                .description("Entdecke den Zocker in dir!")
                .build();
        Category category6 = Category.builder()
                .name("Natur")
                .description("Ob Wald oder Wiese, frische Luft geht immer.")
                .build();
        Category category7 = Category.builder()
                .name("Kino")
                .description("Dein Ticket nach Hollywood")
                .build();
        Category category8 = Category.builder()
                .name("Theater")
                .description("Sein oder nicht sein?")
                .build();
        Category category9 = Category.builder()
                .name("Workshop")
                .description("Schaffe, schaffe, Neues lernen")
                .build();
        Category category10 = Category.builder()
                .name("Computer")
                .description("Mehr als nur Excel und Word")
                .build();
        Category category11 = Category.builder()
                .name("Ganze Familie")
                .description("Weil gemeinsam einfach mehr Spaß macht")
                .build();
        Category category12 = Category.builder()
                .name("Tiere")
                .description("Eine Muh, eine Mäh, eine Täterätätätäh")
                .build();


        List<Category> categoryList1 = new ArrayList<>();
        List<Category> categoryList2 = new ArrayList<>();
        List<Category> categoryList3 = new ArrayList<>();
        List<Category> categoryList4 = new ArrayList<>();
        List<Category> categoryList5 = new ArrayList<>();
        categoryList1.add(category1);
        categoryList2.add(category2);
        categoryList3.add(category3);
        categoryList4.add(category10);
        categoryList5.add(category8);
        categoryList5.add(category11);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);
        categoryRepository.save(category5);
        categoryRepository.save(category6);
        categoryRepository.save(category7);
        categoryRepository.save(category8);
        categoryRepository.save(category9);
        categoryRepository.save(category10);
        categoryRepository.save(category11);
        categoryRepository.save(category12);

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
                .price(0.0f)
                .minAge(6)
                .restricted(true)
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
                .price(0.0f)
                .minAge(0)
                .restricted(false)
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
                .price(19.00f)
                .minAge(8)
                .restricted(true)
                .applicant(applicant3)
                .processor(administrativeUser2)
                .status("offen")
                .build();

        Event event4 = Event.builder()
                .name("Großfeld-Fußball Turnier")
                .address(address1)
                .categories(categoryList1)
                .startDate(new Date(new Date().getTime() + 172800000L))
                .endDate(new Date(new Date().getTime() + 194400000L))
                .price(5.00f)
                .minAge(12)
                .restricted(true)
                .applicant(applicant4)
                .processor(administrativeUser1)
                .status("offen")
                .build();

        Event event5 = Event.builder()
                .name("Computer-Kurs")
                .address(address1)
                .categories(categoryList4)
                .startDate(new Date(new Date().getTime() + 14400000L))
                .endDate(new Date(new Date().getTime() + 36000000L))
                .price(5.00f)
                .minAge(12)
                .restricted(true)
                .applicant(applicant4)
                .processor(administrativeUser1)
                .status("offen")
                .build();

        Event event6 = Event.builder()
                .name("Shakespears Romeo und Julia")
                .address(address1)
                .categories(categoryList5)
                .startDate(new Date(new Date().getTime() + 259200000L))
                .endDate(new Date(new Date().getTime() + 288000000L))
                .price(2.00f)
                .minAge(6)
                .restricted(false)
                .applicant(applicant4)
                .processor(administrativeUser1)
                .status("offen")
                .build();
        // Events speichern
        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);
        eventRepository.save(event4);
        eventRepository.save(event5);
        eventRepository.save(event6);

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
                .password("test")
                .email("test@test.de")
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