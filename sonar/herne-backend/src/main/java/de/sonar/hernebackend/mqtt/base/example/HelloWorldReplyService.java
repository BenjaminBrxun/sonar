package de.sonar.hernebackend.mqtt.base.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sonar.hernebackend.mqtt.base.reply.AbstractMqttReplyService;
import de.sonar.hernebackend.mqtt.base.reply.InvalidReplyStateException;
import de.sonar.hernebackend.mqtt.base.request.MqttRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldReplyService extends AbstractMqttReplyService<String, String> {

    @Autowired
    public HelloWorldReplyService(
            @Qualifier("hello-world_mqttReplyOutboundChannel") MessageChannel mqttReplyOutboundChannel,
            ObjectMapper objectMapper) {
        super(mqttReplyOutboundChannel, objectMapper);
    }

    @Override
    protected String processRequestPayload(String payload) {
        if (!payload.equals("Hello World!")) {
            throw new InvalidReplyStateException("You should say 'Hello World!'!");
        }
        return "Hello World You Too!";
    }

    @ServiceActivator(inputChannel = "hello-world_mqttRequestInboundChannel")
    @Override
    public void handleRequest(Message<MqttRequest<String>> message) {
        super.handleRequest(message);
    }
}
