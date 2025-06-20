package de.sonar.sonar.services;

import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.model.enums.EventStatus;
import de.sonar.sonar.repositories.EventRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventArchivalSchedulerService {

    private final EventRepository eventRepository;
    private final TaskScheduler taskScheduler;

    private static final Set<EventStatus> ARCHIVABLE_STATUSES = Set.of(
            EventStatus.DEPLOYED,
            EventStatus.CANCELLED
    );

    public void scheduleArchival(Event event) {
        if (!ARCHIVABLE_STATUSES.contains(event.getStatus())) {
            throw new IllegalArgumentException("Event must be either DEPLOYED or CANCELLED to be scheduled for archival.");
        }

        if (event.getEndDate() == null) {
            throw new IllegalArgumentException("Event must have an end date to be scheduled for archival.");
        }

        Instant archivalTime = event.getEndDate().toInstant();

        taskScheduler.schedule(() -> {
            if (eventRepository.existsById(event.getId())) {
                Event fresh = eventRepository.findById(event.getId()).orElse(null);
                if (fresh != null && ARCHIVABLE_STATUSES.contains(fresh.getStatus())) {
                    fresh.setStatus(EventStatus.ARCHIVED);
                    eventRepository.save(fresh);
                    log.info("Event with ID {} has been archived", fresh.getId());
                }
            }
        }, archivalTime);
    }

    @PostConstruct
    private void recoverEventsForArchivalOnStartup() {
        log.info("Scheduling events for archival on startup.");
        List<Event> archivableEvents = eventRepository.findAllByStatusIn(ARCHIVABLE_STATUSES);

        for (Event event : archivableEvents) {
            if (event.getEndDate().toInstant().isBefore(Instant.now())) {
                event.setStatus(EventStatus.ARCHIVED);
                eventRepository.save(event);
                log.info("Event with ID {} has been archived during startup recovery", event.getId());
            } else {
                scheduleArchival(event);
                log.info("Event with ID {} has been scheduled for archival", event.getId());
            }
        }
        log.info("Successfully scheduled events for archival on startup.");
    }


}
