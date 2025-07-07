package de.sonar.hernebackend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sonar.hernebackend.model.entity.Event;
import de.sonar.hernebackend.mqtt.base.reply.MqttReply;
import de.sonar.hernebackend.mqtt.base.request.AbstractMqttRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventRegistrationRequestService extends AbstractMqttRequestService<Event, Event> {

    public EventRegistrationRequestService(
            @Qualifier("register-event_mqttRequestOutboundChannel") MessageChannel mqttRequestOutboundChannel,
            ObjectMapper objectMapper) {
        super(mqttRequestOutboundChannel, objectMapper);
    }

    public Event registerNewEvent(Event newEvent) {
        log.info("Send outgoing event registration request.");
        return this.sendRequest(newEvent);
    }

    @ServiceActivator(inputChannel = "register-event_mqttReplyInboundChannel")
    @Override
    public void handleResponse(Message<MqttReply<Event>> message) {
        super.handleResponse(message);
    }

}
