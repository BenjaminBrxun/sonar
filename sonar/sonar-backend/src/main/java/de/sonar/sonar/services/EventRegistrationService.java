package de.sonar.sonar.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.mqtt.base.reply.AbstractMqttReplyService;
import de.sonar.sonar.mqtt.base.reply.RegisterReplyMqttConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RegisterReplyMqttConfig(topic = "register-event")
public class EventRegistrationService extends AbstractMqttReplyService<Event, Event> {

    private final EventLifecycleService eventLifecycleService;

    public EventRegistrationService(
            MessageChannel mqttReplyOutboundChannel,
            EventLifecycleService eventLifecycleService,
            ObjectMapper objectMapper
    ) {
        super(mqttReplyOutboundChannel, objectMapper);
        this.eventLifecycleService = eventLifecycleService;
    }

    @Override
    protected Event processRequestPayload(Event payload) {
        return eventLifecycleService.submitNewEvent(payload);
    }
}
