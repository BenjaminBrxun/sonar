package de.sonar.hernebackend.services;

import de.sonar.hernebackend.model.entity.Event;
import de.sonar.hernebackend.model.enums.EventStatus;
import de.sonar.hernebackend.model.state.InvalidEventStateException;
import de.sonar.hernebackend.mqtt.base.request.AbstractMqttRequestService;
import de.sonar.hernebackend.mqtt.base.request.RegisterRequestMqttConfig;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@RegisterRequestMqttConfig(topic = "cancel-event")
@Service
public class EventCancellationService extends AbstractMqttRequestService<Event, Event> {

    public EventCancellationService(MessageChannel mqttRequestOutboundChannel) {
        super(mqttRequestOutboundChannel);
    }

    public Event cancelEvent(Event event) {
        if (!event.getStatus().equals(EventStatus.DEPLOYED)) {
            throw new InvalidEventStateException("Only events in status " + EventStatus.DEPLOYED + " can be cancelled. Current status: " + event.getStatus());
        }
        return this.sendRequest(event);
    }
}
