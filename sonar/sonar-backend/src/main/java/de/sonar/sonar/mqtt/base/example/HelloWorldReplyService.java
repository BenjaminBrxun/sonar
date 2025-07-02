package de.sonar.sonar.mqtt.base.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sonar.sonar.mqtt.base.reply.AbstractMqttReplyService;
import de.sonar.sonar.mqtt.base.reply.InvalidReplyStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldReplyService extends AbstractMqttReplyService<String, String> {

    @Autowired
    public HelloWorldReplyService(
            MessageChannel mqttReplyOutboundChannel,
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
}
