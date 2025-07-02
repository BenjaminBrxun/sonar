package de.sonar.sonar.mqtt.base.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sonar.sonar.mqtt.base.request.AbstractMqttRequestService;
import de.sonar.sonar.mqtt.base.request.RegisterRequestMqttConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RegisterRequestMqttConfig(topic = "hello-world")
public class HelloWorldRequestService extends AbstractMqttRequestService<String, String> {

    @Autowired
    protected HelloWorldRequestService(
            MessageChannel mqttRequestOutboundChannel,
            ObjectMapper objectMapper) {
        super(mqttRequestOutboundChannel, objectMapper);
    }

    public void helloWorld() {
        String request = "Hello World!";
        log.info("Sending request: {}", request);
        String response = this.sendRequest(request);
        log.info("Received response: {}", response);
    }
}
