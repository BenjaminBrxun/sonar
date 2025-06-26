package de.sonar.sonar.services;

import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.mqtt.base.reply.AbstractMqttReplyService;
import de.sonar.sonar.mqtt.base.reply.RegisterReplyMqttConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RegisterReplyMqttConfig(topic = "proceed-event")
public class EventProcessionService extends AbstractMqttReplyService<Event, Event> {

    private final EventLifecycleService eventLifecycleService;

    public EventProcessionService(
            MessageChannel mqttReplyOutboundChannel,
            EventLifecycleService eventLifecycleService
    ) {
        super(mqttReplyOutboundChannel);
        this.eventLifecycleService = eventLifecycleService;
    }

    @Override
    protected Event processRequestPayload(Event payload) {
        return this.eventLifecycleService.proceedEventLivecycle(payload);
    }
}
