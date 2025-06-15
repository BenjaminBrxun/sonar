package de.sonar.sonar.mqtt.example;

import de.sonar.sonar.mqtt.base.request.RegisterRequesterMqttConfig;
import de.sonar.sonar.mqtt.base.request.AbstractMqttRequesterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RegisterRequesterMqttConfig(topic = "hello-world")
public class HelloWorldRequesterService extends AbstractMqttRequesterService<String, String> {

    @Autowired
    protected HelloWorldRequesterService(MessageChannel mqttRequestOutboundChannel) {
        super(mqttRequestOutboundChannel);
    }

    public void helloWorld() {
        String request = "Hello World!";
        log.info("Sending request: {}", request);
        String response = this.sendRequest(request);
        log.info("Received response: {}", response);
    }
}
