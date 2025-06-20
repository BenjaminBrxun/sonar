package de.sonar.sonar.model.state;


import de.sonar.sonar.model.enums.EventStatus;

public interface EventState {

    void proceed(EventContext context);

    default void delete(EventContext context) {
        context.getEvent().setStatus(EventStatus.DELETED);
        context.setState(new EventDeletedState());
    }

}
