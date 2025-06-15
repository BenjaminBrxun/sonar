package de.sonar.sonar.mqtt.example;

import de.sonar.sonar.mqtt.base.reply.RegisterReplyMqttConfig;
import de.sonar.sonar.mqtt.base.reply.AbstractMqttReplyService;
import de.sonar.sonar.mqtt.base.reply.InvalidReplyStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@RegisterReplyMqttConfig(topic = "hello-world")
public class HelloWorldReplyService extends AbstractMqttReplyService<String, String> {

    @Autowired
    public HelloWorldReplyService(MessageChannel mqttReplyOutboundChannel) {
        super(mqttReplyOutboundChannel);
    }

    @Override
    protected String processRequestPayload(String payload) {
        if (!payload.equals("Hello World!")) {
            throw new InvalidReplyStateException("You should say 'Hello World!'!");
        }
        return "Hello World You Too!";
    }
}
