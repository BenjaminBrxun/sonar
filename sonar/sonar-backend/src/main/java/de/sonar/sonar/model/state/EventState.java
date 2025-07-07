package de.sonar.sonar.model.state;


import de.sonar.sonar.model.enums.EventStatus;

import java.time.OffsetDateTime;

public interface EventState {

    void proceed(EventContext context);

    default void delete(EventContext context) {
        context.getEvent().setStatus(EventStatus.DELETED);
        context.getEvent().setDeletedStateAt(OffsetDateTime.now());
        context.setState(new EventDeletedState());
    }

}
