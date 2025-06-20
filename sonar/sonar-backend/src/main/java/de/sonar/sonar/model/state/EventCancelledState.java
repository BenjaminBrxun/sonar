package de.sonar.sonar.model.state;

import de.sonar.sonar.model.enums.EventStatus;

public class EventCancelledState implements EventState {

    @Override
    public void proceed(EventContext context) {
        context.getEvent().setStatus(EventStatus.ARCHIVED);
        context.setState(new EventArchivedState());
    }

}
