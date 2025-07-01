package de.sonar.hernebackend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sonar.hernebackend.model.entity.Event;
import de.sonar.hernebackend.mqtt.base.request.AbstractMqttRequestService;
import de.sonar.hernebackend.mqtt.base.request.RegisterRequestMqttConfig;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@RegisterRequestMqttConfig(topic = "delete-event")
public class EventDeletionService extends AbstractMqttRequestService<Event, Event> {

    public EventDeletionService(
            MessageChannel mqttRequestOutboundChannel,
            ObjectMapper objectMapper) {
        super(mqttRequestOutboundChannel, objectMapper);
    }

    public void deleteEvent(Event event) {
        this.sendRequest(event);
    }
}
