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
public class EventProcessionService extends AbstractMqttRequestService<Event, Event> {

    public EventProcessionService(
            @Qualifier("process-event_mqttRequestOutboundChannel") MessageChannel mqttRequestOutboundChannel,
            ObjectMapper objectMapper) {
        super(mqttRequestOutboundChannel, objectMapper);
    }

    public Event proceedEvent(Event event) {
        log.info("Send outgoing event procession request.");
        return this.sendRequest(event);
    }

    @ServiceActivator(inputChannel = "process-event_mqttReplyInboundChannel")
    @Override
    public void handleResponse(Message<MqttReply<Event>> message) {
        super.handleResponse(message);
    }
}
