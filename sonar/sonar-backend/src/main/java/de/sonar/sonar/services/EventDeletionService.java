//package de.sonar.sonar.services;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import de.sonar.sonar.model.entity.Event;
//import de.sonar.sonar.mqtt.base.reply.AbstractMqttReplyService;
//import de.sonar.sonar.mqtt.base.reply.RegisterReplyMqttConfig;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.stereotype.Service;
//
//@Service
//@RegisterReplyMqttConfig(topic = "delete-event")
//@Slf4j
//public class EventDeletionService extends AbstractMqttReplyService<Event, Event> {
//
//    private final EventLifecycleService eventLifecycleService;
//
//    public EventDeletionService(
//            MessageChannel mqttReplyOutboundChannel,
//            EventLifecycleService eventLifecycleService,
//            ObjectMapper objectMapper) {
//        super(mqttReplyOutboundChannel, objectMapper);
//        this.eventLifecycleService = eventLifecycleService;
//    }
//
//    @Override
//    protected Event processRequestPayload(Event payload) {
//        log.info("Handle incoming event deletion request.");
//        eventLifecycleService.deleteEvent(payload);
//        return null;
//    }
//}
