package de.sonar.sonar.model.state;

import de.sonar.sonar.model.enums.EventStatus;

public class EventInReviewState implements EventState {

    @Override
    public void proceed(EventContext context) {
        context.getEvent().setStatus(EventStatus.APPROVED);
        context.setState(new EventApprovedState());
    }

    public void decline(EventContext context) {
        context.getEvent().setStatus(EventStatus.DECLINED);
        context.setState(new EventDeclinedState());
    }

}
