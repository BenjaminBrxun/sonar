package de.sonar.sonar.model.state;

import de.sonar.sonar.model.enums.EventStatus;

public class EventApprovedState implements EventState {

    @Override
    public void proceed(EventContext context) {
        context.getEvent().setStatus(EventStatus.DEPLOYED);
        context.setState(new EventDeployedState());
    }

}
