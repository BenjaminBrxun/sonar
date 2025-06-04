package de.sonar.sonar.mqtt.example;

import de.sonar.sonar.datenmodell.Event;
import de.sonar.sonar.mqtt.base.config.RegisterRequesterMqttConfig;
import de.sonar.sonar.mqtt.base.service.AbstractMqttRequesterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RegisterRequesterMqttConfig(topic = "events")
public class EventsMqttRequesterService extends AbstractMqttRequesterService<List<Event>, List<Event>> {

    public EventsMqttRequesterService(MessageChannel mqttRequestOutboundChannel) {
        super(mqttRequestOutboundChannel);
    }

    public void turnChiquitasIntoBananas() {
        Event chiquitaEvent1 = new Event();
        chiquitaEvent1.setName("Chiquita");
        Event chiquitaEvent2 = new Event();
        chiquitaEvent2.setName("Chiquita");
        List<Event> chiquitaEvents = List.of(chiquitaEvent1, chiquitaEvent2);

        List<Event> bananaEvents = this.sendRequest(chiquitaEvents);

        for (Event bananaEvent : bananaEvents) {
            if (!bananaEvent.getName().equals("Banana")) {
                throw new IllegalStateException("Something went wrong! Give me Banana!");
            }
            log.info("I give you Chiquita, you give me {}! Yeah!", bananaEvent.getName());
        }
    }
}
