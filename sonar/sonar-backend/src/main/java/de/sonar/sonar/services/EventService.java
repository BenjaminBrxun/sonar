package de.sonar.sonar.services;

import de.sonar.sonar.model.entity.Category;
import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.model.enums.EventStatus;
import de.sonar.sonar.repositories.EventRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Ruft alle Events aus der Datenbank ab.
     * <p>
     * Ruft nur die Events mit dem Status DEPLOYED und CANCELLED ab.
     *
     * @return alle Events.
     */
    public List<Event> getAllEvents() {
        Specification<Event> eventSpecification =
                (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(cb.or(
                            cb.equal(root.get("status"), EventStatus.DEPLOYED),
                            cb.equal(root.get("status"), EventStatus.CANCELLED)));
                    return cb.and(predicates.toArray(new Predicate[0]));
                };
        return eventRepository.findAll(eventSpecification);
    }

    public Event getEventById(Long id) {
        return eventRepository.getEventById(id);
    }

    /**
     * Ruft für alle Events mit passendem Namen aus der Datenbank ab.
     * <p>
     * Ruft nur die Events mit dem Status DEPLOYED und CANCELLED ab.
     *
     * @param name Begriff, der im Titel der Events enthalten ist
     * @return alle Events mit passendem Namen.
     */
    public List<Event> findAllByName(String name) {
        Specification<Event> eventSpecification =
                (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(cb.or(
                            cb.equal(root.get("status"), EventStatus.DEPLOYED),
                            cb.equal(root.get("status"), EventStatus.CANCELLED)));
                    predicates.add(cb.like(cb.lower(root.get("name")), name.toLowerCase()));
                    return cb.and(predicates.toArray(new Predicate[0]));
                };
        return eventRepository.findAll(eventSpecification);
    }

    public List<Event> findAllWithMatchingCriteria(List<Long> categories, String name, Long startDateInMilliseconds, Long endDateInMilliseconds, Float price, Boolean restricted, Integer minAge) {
        Date startDate = (startDateInMilliseconds == null) ? null : new Date(startDateInMilliseconds);
        Date endDate = (endDateInMilliseconds == null) ? null : new Date(endDateInMilliseconds);
        return eventRepository.findAll(
                buildEventFilter(name, categories, startDate, endDate, price, restricted, minAge)
        );
    }

    /**
     * Erzeugt abhängig der übergebenen Parameter eine SQL-Abfrage, welche dann zurückgegeben wird.
     * <p>
     * Ruft nur die Events mit dem Status DEPLOYED und CANCELLED ab.
     *
     * @param name        Begriff, der im Titel der Events enthalten ist
     * @param categoryIds IDs der Kategorien
     * @param startDate   Anfangsdatum
     * @param endDate     Enddatum
     * @param price       Kosten
     * @param restricted  Anmeldung nötig
     * @param minAge      Altersgrenze
     * @return eine Spezifikation, welche im {@link EventRepository} genutzt wird, um die passenden Events zu finden.
     */
    private Specification<Event> buildEventFilter(
            String name,
            List<Long> categoryIds,
            Date startDate,
            Date endDate,
            Float price,
            Boolean restricted,
            Integer minAge
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (categoryIds != null && !categoryIds.isEmpty()) {
                Join<Event, Category> categoryJoin = root.join("categories");
                predicates.add(categoryJoin.get("id").in(categoryIds));
            }

            if (startDate != null && endDate != null) {
                predicates.add(cb.between(root.get("startDate"), startDate, endDate));
            } else if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), startDate));
            } else if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), endDate));
            }

            if (price != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), price));
            }

            if (restricted != null) {
                predicates.add(cb.equal(root.get("restricted"), restricted));
            }

            if (minAge != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("minAge"), minAge));
            }

            predicates.add(cb.or(
                    cb.equal(root.get("status"), EventStatus.DEPLOYED),
                    cb.equal(root.get("status"), EventStatus.CANCELLED)));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
