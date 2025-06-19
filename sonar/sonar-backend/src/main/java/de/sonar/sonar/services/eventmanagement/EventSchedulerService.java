package de.sonar.sonar.services.eventmanagement;

import de.sonar.sonar.model.Event;
import de.sonar.sonar.model.state.DeleteEventState;
import de.sonar.sonar.model.state.DeployedEventState;
import de.sonar.sonar.repositories.EventRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class EventSchedulerService {

    private final EventManagementService eventManagementService;
    private final EventRepository eventRepository;
    private final TaskScheduler taskScheduler;
    private final ConcurrentHashMap<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @PostConstruct
    public void initializeScheduledTasks() {
        // Schedule all deployed events that haven't ended yet
        eventRepository.findAllByEventState(new DeployedEventState())
                .stream()
                .filter(event -> event.getEndDate().after(new Date()))
                .forEach(this::scheduleArchiving);

        // Schedule final deletion for all deleted events
        eventRepository.findAllByEventState(new DeployedEventState())
                .forEach(this::scheduleDeletion);
    }

    public void scheduleArchiving(Event event) {
        cancelExistingTask(event.getId());

        if (event.getEndDate().after(new Date())) {
            ScheduledFuture<?> future = taskScheduler.schedule(
                    () -> eventManagementService.processEvent(event),
                    event.getEndDate()
            );
            scheduledTasks.put(event.getId(), future);
        }
    }

    public void scheduleDeletion(Event event) {
        cancelExistingTask(event.getId());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 30);
        Date deletionDate = cal.getTime();

        ScheduledFuture<?> future = taskScheduler.schedule(
                () -> {
                    eventRepository.delete(event);
                    scheduledTasks.remove(event.getId());
                },
                deletionDate
        );
        scheduledTasks.put(event.getId(), future);
    }

    private void cancelExistingTask(Long eventId) {
        ScheduledFuture<?> existingTask = scheduledTasks.get(eventId);
        if (existingTask != null) {
            existingTask.cancel(false);
            scheduledTasks.remove(eventId);
        }
    }


}
