package de.sonar.sonar.model.state;

import de.sonar.sonar.model.enums.EventStatus;

public class EventDeclinedState implements EventState {

    @Override
    public void proceed(EventContext context) {
        context.getEvent().setStatus(EventStatus.UNDER_EDITING);
        context.setState(new UnderEditingEventState());
    }

}
