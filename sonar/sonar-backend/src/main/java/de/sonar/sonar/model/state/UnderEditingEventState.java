package de.sonar.sonar.model.state;


import de.sonar.sonar.model.enums.EventStatus;

public class UnderEditingEventState implements EventState {

    @Override
    public void proceed(EventContext context) {
        context.getEvent().setStatus(EventStatus.IN_REVIEW);
        context.setState(new EventInReviewState());
    }

}
