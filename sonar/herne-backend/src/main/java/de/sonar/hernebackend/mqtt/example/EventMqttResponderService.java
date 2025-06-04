package de.sonar.hernebackend.mqtt.example;

import de.sonar.hernebackend.datenmodell.Event;
import de.sonar.hernebackend.mqtt.base.config.RegisterResponderMqttConfig;
import de.sonar.hernebackend.mqtt.base.service.AbstractMqttResponderService;
import de.sonar.hernebackend.mqtt.base.service.InvalidResponseStateException;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@RegisterResponderMqttConfig(topic = "event")
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
