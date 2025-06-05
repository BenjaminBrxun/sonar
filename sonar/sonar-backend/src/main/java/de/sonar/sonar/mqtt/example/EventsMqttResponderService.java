package de.sonar.sonar.mqtt.example;

import de.sonar.sonar.datenmodell.Event;
import de.sonar.sonar.mqtt.base.config.RegisterResponderMqttConfig;
import de.sonar.sonar.mqtt.base.service.AbstractMqttResponderService;
import de.sonar.sonar.mqtt.base.service.InvalidResponseStateException;
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
