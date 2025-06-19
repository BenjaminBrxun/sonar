package de.sonar.sonar.model.state;

import de.sonar.sonar.model.Event;
import lombok.RequiredArgsConstructor;

public class ApprovedEventState implements EventState {

    @Override
    public void handleEvent(Event event) {
        event.setEventState(new DeployedEventState());
    }

}
