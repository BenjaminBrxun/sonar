package de.sonar.sonar.services;

import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.mqtt.base.reply.AbstractMqttReplyService;
import de.sonar.sonar.mqtt.base.reply.RegisterReplyMqttConfig;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@RegisterReplyMqttConfig(topic = "decline-event")
public class EventDeclineService extends AbstractMqttReplyService<Event, Event> {

    private final EventLifecycleService eventLifecycleService;

    public EventDeclineService(
            MessageChannel mqttReplyOutboundChannel,
            EventLifecycleService eventLifecycleService) {
        super(mqttReplyOutboundChannel);
        this.eventLifecycleService = eventLifecycleService;
    }

    @Override
    protected Event processRequestPayload(Event payload) {
        return eventLifecycleService.declineEvent(payload);
    }
}
