package de.sonar.hernebackend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sonar.hernebackend.model.entity.Event;
import de.sonar.hernebackend.mqtt.base.request.AbstractMqttRequestService;
import de.sonar.hernebackend.mqtt.base.request.RegisterRequestMqttConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@RegisterRequestMqttConfig(topic = "proceed-event")
@Slf4j
public class EventProcessionService extends AbstractMqttRequestService<Event, Event> {

    public EventProcessionService(
            MessageChannel mqttRequestOutboundChannel,
            ObjectMapper objectMapper) {
        super(mqttRequestOutboundChannel, objectMapper);
    }

    public Event proceedEvent(Event event) {
        log.info("Send outgoing event procession request.");
        return this.sendRequest(event);
    }
}
