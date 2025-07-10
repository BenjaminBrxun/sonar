package de.sonar.hernebackend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sonar.hernebackend.model.entity.Event;
import de.sonar.hernebackend.model.enums.EventStatus;
import de.sonar.hernebackend.model.state.InvalidEventStateException;
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
public class EventDeclineService extends AbstractMqttRequestService<Event, Event> {

    public EventDeclineService(
            @Qualifier("decline-event_mqttRequestOutboundChannel") MessageChannel mqttRequestOutboundChannel,
            ObjectMapper objectMapper) {
        super(mqttRequestOutboundChannel, objectMapper);
    }

    public Event declineEvent(Event event) {
        if (!event.getStatus().equals(EventStatus.IN_REVIEW)) {
            throw new InvalidEventStateException("Only events in status " + EventStatus.IN_REVIEW + " can be declined. Current status: " + event.getStatus());
        }
        log.info("Send outgoing event decline request.");
        return this.sendRequest(event);
    }

    @ServiceActivator(inputChannel = "decline-event_mqttReplyInboundChannel")
    @Override
    public void handleResponse(Message<MqttReply<Event>> message) {
        super.handleResponse(message);
    }

}
