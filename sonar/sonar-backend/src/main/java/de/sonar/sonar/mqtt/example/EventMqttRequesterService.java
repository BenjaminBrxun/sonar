package de.sonar.sonar.mqtt.example;

import de.sonar.sonar.datenmodell.Event;
import de.sonar.sonar.mqtt.base.config.RegisterRequesterMqttConfig;
import de.sonar.sonar.mqtt.base.service.AbstractMqttRequesterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RegisterRequesterMqttConfig(topic = "event")
public class EventMqttRequesterService extends AbstractMqttRequesterService<Event, Event> {

    public EventMqttRequesterService(MessageChannel mqttRequestOutboundChannel) {
        super(mqttRequestOutboundChannel);
    }

    public void turnChiquitaIntoBanana() {
        Event chiquitaEvent = new Event();
        chiquitaEvent.setName("Chiquita");

        Event bananaEvent = this.sendRequest(chiquitaEvent);

        if (!bananaEvent.getName().equals("Banana")) {
            throw new IllegalStateException("Something went wrong! Give me Banana!");
        }
        log.info("I give you Chiquita, you give me {}! Yeah!", bananaEvent.getName());

    }
}
