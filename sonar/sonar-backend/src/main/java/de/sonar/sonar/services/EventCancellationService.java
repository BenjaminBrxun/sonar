package de.sonar.sonar.services;

import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.mqtt.base.reply.AbstractMqttReplyService;
import de.sonar.sonar.mqtt.base.reply.RegisterReplyMqttConfig;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@RegisterReplyMqttConfig(topic = "cancel-event")
@Service
public class EventCancellationService extends AbstractMqttReplyService<Event, Event> {

    private final EventLifecycleService eventLifecycleService;

    public EventCancellationService(
            MessageChannel mqttReplyOutboundChannel,
            EventLifecycleService eventLifecycleService) {
        super(mqttReplyOutboundChannel);
        this.eventLifecycleService = eventLifecycleService;
    }

    @Override
    protected Event processRequestPayload(Event payload) {
        return eventLifecycleService.cancelEvent(payload);
    }
}
