package de.sonar.sonar;

import de.sonar.sonar.model.entity.*;
import de.sonar.sonar.model.enums.EventStatus;
import de.sonar.sonar.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final OrganizerRepository organizerRepository;
    private final AdministrativeUserRepository administrativeUserRepository;
    private final UserRepository userRepository;
    private final InterestsProfileRepository interestsProfileRepository;

    @Override
    public void run(String... args) {

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

        Address address1 = new Address("Herne", "In der Siedlung", "12", "44625", "Mitte");
        Address address2 = new Address("Herne", "Heerstraße", "18", "44653", "Crange");
        Address address3 = new Address("Herne", "Wilhelmstraße", "26", "44649", "Wanne-Eickel");
        Address address4 = new Address("Herne", "Lange Straße", "1", "44627", "Holthausen");
        Address address5 = new Address("Bochum", "Günnigfelder Straße", "251", "44793", "Hordel");
        Address address6 = new Address("Herne", "Bergstraße", "27", "44625", "Süd");
        Address address7 = new Address("Herne", "Wiescherstraße", "118A", "44625", "Süd");
        Address address8 = new Address("Herne", "Karl-Brandt-Weg", "5", "44629", "Baukau");

        Organizer applicant1 = Organizer.builder()
                .organisation("Stadt Herne")
                .build();
        Organizer applicant2 = Organizer.builder()
                .organisation("Schwimm- und Sportverein Herne-Süd")
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
                .price(0.0f)
                .minAge(6)
                .restricted(true)
                .startDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(1749119967000L), ZoneId.systemDefault()))
                .endDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(1749148767000L), ZoneId.systemDefault()))
                .applicant(applicant1)
                .status(EventStatus.DEPLOYED)
                .processor(administrativeUser1)
                .build();
        Event event2 = Event.builder()
                .name("Familienfest im Stadtpark")
                .address(address2)
                .categories(categoryList2)
                .price(0.0f)
                .minAge(0)
                .restricted(false)
                .startDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(1749384000000L), ZoneId.systemDefault()))
                .endDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(1749405600000L), ZoneId.systemDefault()))
                .applicant(applicant2)
                .status(EventStatus.DEPLOYED)
                .processor(administrativeUser2)
                .build();

        Event event3 = Event.builder()
                .name("Ferienlager")
                .address(address3)
                .categories(categoryList3)
                .price(19.00f)
                .minAge(8)
                .restricted(true)
                .startDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(1749384000000L), ZoneId.systemDefault()))
                .endDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(1749751200000L), ZoneId.systemDefault()))
                .applicant(applicant3)
                .status(EventStatus.DEPLOYED)
                .processor(administrativeUser2)
                .build();

        Event event4 = Event.builder()
                .name("Großfeld-Fußball Turnier")
                .address(address4)
                .categories(categoryList1)
                .startDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(172800000L), ZoneId.systemDefault()))
                .endDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(194400000L), ZoneId.systemDefault()))
                .price(5.00f)
                .minAge(12)
                .restricted(true)
                .applicant(applicant4)
                .processor(administrativeUser1)
                .status(EventStatus.DEPLOYED)
                .build();

        Event event5 = Event.builder()
                .name("Computer-Kurs")
                .address(address5)
                .categories(categoryList4)
                .startDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(14400000L), ZoneId.systemDefault()))
                .endDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(36000000L), ZoneId.systemDefault()))
                .price(5.00f)
                .minAge(12)
                .restricted(true)
                .applicant(applicant4)
                .processor(administrativeUser1)
                .status(EventStatus.DEPLOYED)
                .build();

        Event event6 = Event.builder()
                .name("Shakespears Romeo und Julia")
                .address(address6)
                .categories(categoryList5)
                .startDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(259200000L), ZoneId.systemDefault()))
                .endDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(288000000L), ZoneId.systemDefault()))
                .price(2.00f)
                .minAge(6)
                .restricted(false)
                .startDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(1749119967000L), ZoneId.systemDefault()))
                .endDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(1749148767000L), ZoneId.systemDefault()))
                .applicant(applicant4)
                .status(EventStatus.DEPLOYED)
                .processor(administrativeUser1)
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