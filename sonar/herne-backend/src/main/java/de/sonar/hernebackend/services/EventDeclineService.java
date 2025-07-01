package de.sonar.hernebackend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sonar.hernebackend.model.entity.Event;
import de.sonar.hernebackend.model.enums.EventStatus;
import de.sonar.hernebackend.model.state.InvalidEventStateException;
import de.sonar.hernebackend.mqtt.base.request.AbstractMqttRequestService;
import de.sonar.hernebackend.mqtt.base.request.RegisterRequestMqttConfig;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@RegisterRequestMqttConfig(topic = "decline-event")
public class EventDeclineService extends AbstractMqttRequestService<Event, Event> {

    public EventDeclineService(
            MessageChannel mqttRequestOutboundChannel,
            ObjectMapper objectMapper) {
        super(mqttRequestOutboundChannel, objectMapper);
    }

    public Event declineEvent(Event event) {
        if (!event.getStatus().equals(EventStatus.IN_REVIEW)) {
            throw new InvalidEventStateException("Only events in status " + EventStatus.IN_REVIEW + " can be declined. Current status: " + event.getStatus());
        }
        return this.sendRequest(event);
    }

}
