package de.sonar.hernebackend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sonar.hernebackend.model.entity.Event;
import de.sonar.hernebackend.mqtt.base.request.AbstractMqttRequestService;
import de.sonar.hernebackend.mqtt.base.request.RegisterRequestMqttConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@RegisterRequestMqttConfig(topic = "register-event")
@Service
@Slf4j
public class EventRegistrationService extends AbstractMqttRequestService<Event, Event> {

    public EventRegistrationService(
            MessageChannel mqttRequestOutboundChannel,
            ObjectMapper objectMapper) {
        super(mqttRequestOutboundChannel, objectMapper);
    }

    public Event registerNewEvent(Event newEvent) {
        log.info("Send outgoing event registration request.");
        return this.sendRequest(newEvent);
    }

}
