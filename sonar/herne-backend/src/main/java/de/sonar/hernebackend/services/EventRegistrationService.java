package de.sonar.hernebackend.services;

import de.sonar.hernebackend.model.entity.Event;
import de.sonar.hernebackend.mqtt.base.request.AbstractMqttRequestService;
import de.sonar.hernebackend.mqtt.base.request.RegisterRequestMqttConfig;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@RegisterRequestMqttConfig(topic = "register-event")
@Service
public class EventRegistrationService extends AbstractMqttRequestService<Event, Event> {

    public EventRegistrationService(MessageChannel mqttRequestOutboundChannel) {
        super(mqttRequestOutboundChannel);
    }

    public Event registerNewEvent(Event newEvent) {
        return this.sendRequest(newEvent);
    }

}
