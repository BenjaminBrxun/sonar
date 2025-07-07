package de.sonar.sonar.mqtt.base.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sonar.sonar.mqtt.base.reply.MqttReply;
import de.sonar.sonar.mqtt.base.request.AbstractMqttRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HelloWorldRequestService extends AbstractMqttRequestService<String, String> {

    @Autowired
    protected HelloWorldRequestService(
            @Qualifier("hello-world_mqttRequestOutboundChannel") MessageChannel mqttRequestOutboundChannel,
            ObjectMapper objectMapper) {
        super(mqttRequestOutboundChannel, objectMapper);
    }

    public void helloWorld() {
        String request = "Hello World!";
        log.info("Sending request: {}", request);
        String response = this.sendRequest(request);
        log.info("Received response: {}", response);
    }

    @ServiceActivator(inputChannel = "hello-world_mqttReplyInboundChannel")
    @Override
    public void handleResponse(Message<MqttReply<String>> message) {
        super.handleResponse(message);
    }
}
