package de.sonar.sonar.model.state;

import de.sonar.sonar.model.Event;

public class EventInReviewState implements EventState {

    @Override
    public void handleEvent(Event event) {
        event.setEventState(new ApprovedEventState());
    }

    public void disapproveEvent(Event event) {
        this.deleteEvent(event);
    }

}
