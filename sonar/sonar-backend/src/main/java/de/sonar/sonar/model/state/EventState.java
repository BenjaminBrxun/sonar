package de.sonar.sonar.model.state;

import de.sonar.sonar.model.Event;

public interface EventState {

    // Todo: Optional: Send notification, that the event is state is changed (email, websocket, app notification)
    void handleEvent(Event event);

    default void deleteEvent(Event event) {
        event.setEventState(new DeleteEventState());
    }

}
