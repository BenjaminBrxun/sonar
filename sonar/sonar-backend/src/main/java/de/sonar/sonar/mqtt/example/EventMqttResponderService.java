package de.sonar.sonar.mqtt.example;

import de.sonar.sonar.datenmodell.Event;
import de.sonar.sonar.mqtt.base.config.RegisterResponderMqttConfig;
import de.sonar.sonar.mqtt.base.service.AbstractMqttResponderService;
import de.sonar.sonar.mqtt.base.service.InvalidResponseStateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@RegisterResponderMqttConfig(topic = "event")
@Slf4j
public class EventMqttResponderService extends AbstractMqttResponderService<Event, Event> {


    public EventMqttResponderService(MessageChannel mqttReplyOutboundChannel) {
        super(mqttReplyOutboundChannel);
    }

    @Override
    protected Event processRequestPayload(Event payload) {
        if (!payload.getName().equals("Chiquita")) {
            throw new InvalidResponseStateException("You should give me 'Chiquita'! I give you 'Banana' instead!");
        }

        payload.setName("Banana");
        return payload;
    }

}
