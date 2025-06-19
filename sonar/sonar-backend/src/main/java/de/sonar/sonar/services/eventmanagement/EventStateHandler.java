package de.sonar.sonar.services.eventmanagement;

import de.sonar.sonar.model.Event;
import de.sonar.sonar.model.state.DeleteEventState;
import de.sonar.sonar.model.state.DeployedEventState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventStateHandler {

    private final EventSchedulerService schedulerService;

    public void handleStateTransition(Event event) {
        if (event.getEventState() instanceof DeployedEventState) {
            schedulerService.scheduleArchiving(event);
        } else if (event.getEventState() instanceof DeleteEventState) {
            schedulerService.scheduleDeletion(event);
        }
    }
}
