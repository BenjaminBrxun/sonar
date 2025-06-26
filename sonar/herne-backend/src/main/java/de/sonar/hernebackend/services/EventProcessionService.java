package de.sonar.hernebackend.services;

import de.sonar.hernebackend.model.entity.Event;
import de.sonar.hernebackend.mqtt.base.request.AbstractMqttRequestService;
import de.sonar.hernebackend.mqtt.base.request.RegisterRequestMqttConfig;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@RegisterRequestMqttConfig(topic = "proceed-event")
public class EventProcessionService extends AbstractMqttRequestService<Event, Event> {

    public EventProcessionService(MessageChannel mqttRequestOutboundChannel) {
        super(mqttRequestOutboundChannel);
    }

    public Event proceedEvent(Event event) {
        return this.sendRequest(event);
    }
}
