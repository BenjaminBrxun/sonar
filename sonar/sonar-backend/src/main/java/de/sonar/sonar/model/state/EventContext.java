package de.sonar.sonar.model.state;

import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.model.enums.EventStatus;
import lombok.Getter;
import lombok.Setter;

public class EventContext {

    @Getter
    private final Event event;

    @Getter
    @Setter
    private EventState state;

    public EventContext(Event event) {
        this.event = event;
        this.state = resolveState(event.getStatus());
    }

    private EventState resolveState(EventStatus status) {
        return switch (status) {
            case UNDER_EDITING -> new UnderEditingEventState();
            case IN_REVIEW -> new EventInReviewState();
            case APPROVED -> new EventApprovedState();
            case DECLINED -> new EventDeclinedState();
            case DEPLOYED -> new EventDeployedState();
            case CANCELLED -> new EventCancelledState();
            case ARCHIVED -> new EventArchivedState();
            case DELETED -> new EventDeletedState();
        };
    }

    public void proceed() {
        state.proceed(this);
    }

    public void delete() {
        state.delete(this);
    }

}
