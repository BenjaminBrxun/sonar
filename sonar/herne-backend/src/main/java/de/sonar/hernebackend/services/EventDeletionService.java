//package de.sonar.hernebackend.services;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import de.sonar.hernebackend.model.entity.Event;
//import de.sonar.hernebackend.mqtt.base.request.AbstractMqttRequestService;
//import de.sonar.hernebackend.mqtt.base.request.RegisterRequestMqttConfig;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.stereotype.Service;
//
//@Service
//@RegisterRequestMqttConfig(topic = "delete-event")
//@Slf4j
//public class EventDeletionService extends AbstractMqttRequestService<Event, Event> {
//
//    public EventDeletionService(
//            MessageChannel mqttRequestOutboundChannel,
//            ObjectMapper objectMapper) {
//        super(mqttRequestOutboundChannel, objectMapper);
//    }
//
//    public void deleteEvent(Event event) {
//        log.info("Send outgoing event deletion request.");
//        this.sendRequest(event);
//    }
//}
