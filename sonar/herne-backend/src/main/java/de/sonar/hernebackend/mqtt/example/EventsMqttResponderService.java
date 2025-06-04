package de.sonar.hernebackend.mqtt.example;

import de.sonar.hernebackend.datenmodell.Event;
import de.sonar.hernebackend.mqtt.base.config.RegisterResponderMqttConfig;
import de.sonar.hernebackend.mqtt.base.service.AbstractMqttResponderService;
import de.sonar.hernebackend.mqtt.base.service.InvalidResponseStateException;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RegisterResponderMqttConfig(topic = "events")
public class EventsMqttResponderService extends AbstractMqttResponderService<List<Event>, List<Event>> {

    public EventsMqttResponderService(MessageChannel mqttReplyOutboundChannel) {
        super(mqttReplyOutboundChannel);
    }

    @Override
    protected List<Event> processRequestPayload(List<Event> payload) {
        for (Event event : payload) {
            if (!event.getName().equals("Chiquita")) {
                throw new InvalidResponseStateException("You should give me 'Chiquita'! I give you 'Banana' instead!");
            }
            event.setName("Banana");
        }
        return payload;
    }
}
