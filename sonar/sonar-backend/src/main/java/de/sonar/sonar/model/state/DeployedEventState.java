package de.sonar.sonar.model.state;

import de.sonar.sonar.model.Event;

public class DeployedEventState implements EventState {

    @Override
    public void handleEvent(Event event) {
        event.setEventState(new ArchivedEventState());
    }
}
