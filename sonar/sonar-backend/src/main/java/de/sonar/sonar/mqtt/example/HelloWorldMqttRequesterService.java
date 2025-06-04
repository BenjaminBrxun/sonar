package de.sonar.sonar.mqtt.example;

import de.sonar.sonar.mqtt.base.config.RegisterRequesterMqttConfig;
import de.sonar.sonar.mqtt.base.service.AbstractMqttRequesterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RegisterRequesterMqttConfig(topic = "hello-world")
public class HelloWorldMqttRequesterService extends AbstractMqttRequesterService<String, String> {

    @Autowired
    protected HelloWorldMqttRequesterService(MessageChannel mqttRequestOutboundChannel) {
        super(mqttRequestOutboundChannel);
    }

    public void helloWorld() {
        String response = this.sendRequest("Hello World!");
        log.info("Received response: {}", response);
    }
}
