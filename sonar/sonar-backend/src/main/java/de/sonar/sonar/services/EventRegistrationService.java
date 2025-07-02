package de.sonar.sonar.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.mqtt.base.reply.AbstractMqttReplyService;
import de.sonar.sonar.mqtt.base.request.MqttRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventRegistrationService extends AbstractMqttReplyService<Event, Event> {

    private final EventLifecycleService eventLifecycleService;

    public EventRegistrationService(
            @Qualifier("register-event_mqttReplyOutboundChannel") MessageChannel mqttReplyOutboundChannel,
            EventLifecycleService eventLifecycleService,
            ObjectMapper objectMapper
    ) {
        super(mqttReplyOutboundChannel, objectMapper);
        this.eventLifecycleService = eventLifecycleService;
    }

    @Override
    protected Event processRequestPayload(Event payload) {
        log.info("Handle incoming event registration request.");
        return eventLifecycleService.submitNewEvent(payload);
    }

    @ServiceActivator(inputChannel = "register-event_mqttRequestInboundChannel")
    @Override
    public void handleRequest(Message<MqttRequest<Event>> message) {
        super.handleRequest(message);
    }
}
