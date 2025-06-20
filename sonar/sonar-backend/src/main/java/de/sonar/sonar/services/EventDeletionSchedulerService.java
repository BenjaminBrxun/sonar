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
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventDeletionSchedulerService {

    private final EventRepository eventRepository;
    private final TaskScheduler taskScheduler;

    public void scheduleDeletion(Event event) {
        if (event.getStatus() != EventStatus.DELETED) {
            throw new IllegalArgumentException("Event must be marked as deleted before it can be scheduled for deletion.");
        }

        final Event eventToSchedule;
        if (event.getDeletedStateAt() == null) {
            event.setDeletedStateAt(OffsetDateTime.now());
            eventToSchedule = eventRepository.save(event);
        } else {
            eventToSchedule = event;
        }

        Instant deletionTime = event.getDeletedStateAt()
                .plusDays(30)
                .toInstant();

        taskScheduler.schedule(() -> {
            if (eventRepository.existsById(eventToSchedule.getId())) {
                Event fresh = eventRepository.findById(eventToSchedule.getId()).orElse(null);
                if (fresh != null && fresh.getStatus() == EventStatus.DELETED) {
                    eventRepository.deleteById(fresh.getId());
                }
            }
        }, deletionTime);
    }

    @PostConstruct
    private void recoverDeletedEventsOnStartup() {
        log.info("Schedule events for deletion on startup.");
        List<Event> deleted = eventRepository.findAllDeletedEvents();

        for (Event deletedEvent : deleted) {
            final Event eventToRecover;
            if (deletedEvent.getDeletedStateAt() == null) {
                deletedEvent.setDeletedStateAt(OffsetDateTime.now());
                eventToRecover = eventRepository.save(deletedEvent);
            } else {
                eventToRecover = deletedEvent;
            }

            Instant deletionTime = eventToRecover.getDeletedStateAt()
                    .plusDays(30)
                    .toInstant();

            if (deletionTime.isBefore(Instant.now())) {
                eventRepository.deleteById(eventToRecover.getId());
            } else {
                scheduleDeletion(eventToRecover);
            }
        }
        log.info("Successfully scheduled events for deletion on startup.");
    }
}

