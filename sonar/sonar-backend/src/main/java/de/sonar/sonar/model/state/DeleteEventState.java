package de.sonar.sonar.model.state;

import de.sonar.sonar.model.Event;

public class DeleteEventState implements EventState {

    @Override
    public void handleEvent(Event event) {
        event.setEventState(new NewEventState());
    }
}
