package de.sonar.sonar.mqtt.example.goodbye;

import de.sonar.sonar.mqtt.base.AbstractMqttRequesterService;
import de.sonar.sonar.mqtt.base.config.RegisterRequesterMqttConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RegisterRequesterMqttConfig(topic = "goodbye-world")
public class GoodbyeWorldMqttRequesterService extends AbstractMqttRequesterService<String, String> {

    @Autowired
    protected GoodbyeWorldMqttRequesterService(MessageChannel mqttRequestOutboundChannel) {
        super(mqttRequestOutboundChannel);
    }

    @Override
    protected String getTopic() {
        return "goodbye-world";
    }

    public void goodbyeWorld() {
        String response = this.sendRequest("Goodbye World!");
        log.info("Received response: {}", response);
    }

}
