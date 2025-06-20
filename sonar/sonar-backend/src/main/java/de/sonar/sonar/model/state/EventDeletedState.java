package de.sonar.sonar.model.state;

import de.sonar.sonar.model.enums.EventStatus;

public class EventDeletedState implements EventState {

    @Override
    public void proceed(EventContext context) {
        context.getEvent().setStatus(EventStatus.UNDER_EDITING);
        context.getEvent().setDeletedStateAt(null);
        context.setState(new UnderEditingEventState());
    }

    @Override
    public void delete(EventContext context) {
        throw new InvalidEventStateException("Cannot delete an event that is already deleted.");
    }
}
