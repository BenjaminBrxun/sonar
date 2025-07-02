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
public class EventDeletionService extends AbstractMqttRequestService<Event, Event> {

    public EventDeletionService(
            @Qualifier("delete-event_mqttRequestOutboundChannel") MessageChannel mqttRequestOutboundChannel,
            ObjectMapper objectMapper) {
        super(mqttRequestOutboundChannel, objectMapper);
    }

    public Event deleteEvent(Event event) {
        log.info("Send outgoing event deletion request.");
        return this.sendRequest(event);
    }

    @ServiceActivator(inputChannel = "delete-event_mqttReplyInboundChannel")
    @Override
    public void handleResponse(Message<MqttReply<Event>> message) {
        super.handleResponse(message);
    }

}
