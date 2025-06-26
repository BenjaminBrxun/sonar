package de.sonar.sonar.services;

import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.mqtt.base.reply.AbstractMqttReplyService;
import de.sonar.sonar.mqtt.base.reply.RegisterReplyMqttConfig;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@RegisterReplyMqttConfig(topic = "delete-event")
public class EventDeletionService extends AbstractMqttReplyService<Event, Event> {

    private final EventLifecycleService eventLifecycleService;

    public EventDeletionService(
            MessageChannel mqttReplyOutboundChannel,
            EventLifecycleService eventLifecycleService) {
        super(mqttReplyOutboundChannel);
        this.eventLifecycleService = eventLifecycleService;
    }

    @Override
    protected Event processRequestPayload(Event payload) {
        eventLifecycleService.deleteEvent(payload);
        // Todo: Add placeholder for generic null values instead
        return null;
    }
}
