package de.sonar.sonar.services;

import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.model.enums.EventStatus;
import de.sonar.sonar.model.state.*;
import de.sonar.sonar.repositories.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventLifecycleService {

    private final EventRepository eventRepository;
    private final EventDeletionSchedulerService eventDeletionSchedulerService;
    private final EventArchivalSchedulerService eventArchivalSchedulerService;

    @Transactional
    public Event submitNewEvent(Event event) {
        validateNewEvent(event);
        return eventRepository.save(event);
    }

    @Transactional
    public Event proceedEventLivecycle(Event event) {
        validateExistingEvent(event);
        EventContext context = new EventContext(event);
        context.proceed();
        if (context.getState() instanceof EventDeployedState) {
            eventArchivalSchedulerService.scheduleArchival(event);
        }
        return eventRepository.save(event);
    }

    @Transactional
    public Event declineEvent(Event event) {
        validateExistingEvent(event);
        if (!event.getStatus().equals(EventStatus.IN_REVIEW)) {
            throw new InvalidEventStateException("Only events in status " + EventStatus.IN_REVIEW + " can be declined. Current status: " + event.getStatus());
        }

        EventContext context = new EventContext(event);
        EventState state = context.getState();

        if (state instanceof EventInReviewState inReviewState) {
            inReviewState.decline(context);
            eventRepository.save(event);
        } else {
            throw new InvalidEventStateException("Unexpected internal event state mismatch.");
        }
        return event;
    }

    @Transactional
    public Event cancelEvent(Event event) {
        validateExistingEvent(event);
        if (!event.getStatus().equals(EventStatus.DEPLOYED)) {
            throw new InvalidEventStateException("Only events in status " + EventStatus.DEPLOYED + " can be cancelled. Current status: " + event.getStatus());
        }

        EventContext context = new EventContext(event);
        EventState state = context.getState();

        if (state instanceof EventDeployedState deployedState) {
            deployedState.cancel(context);
            eventRepository.save(event);
        } else {
            throw new InvalidEventStateException("Unexpected internal event state mismatch.");
        }
        return event;
    }

    @Transactional
    public Event deleteEvent(Event event) {
        validateExistingEvent(event);
        EventContext context = new EventContext(event);
        context.delete();
        eventDeletionSchedulerService.scheduleDeletion(event);
        return event;
    }

    private static void validateNewEvent(Event event) {
        if (!event.isNew()) {
            throw new InvalidEventStateException("New events must not have an ID.");
        }
        if (!event.getStatus().equals(EventStatus.UNDER_EDITING)) {
            throw new InvalidEventStateException("New events must be in status " + EventStatus.UNDER_EDITING + ".");
        }
    }

    private void validateExistingEvent(Event event) {
        if (!eventExists(event.getId())) {
            throw new InvalidEventStateException("Event with ID " + event.getId() + " does not exist.");
        }
    }

    private boolean eventExists(Long eventId) {
        return eventRepository.existsById(eventId);
    }
}
