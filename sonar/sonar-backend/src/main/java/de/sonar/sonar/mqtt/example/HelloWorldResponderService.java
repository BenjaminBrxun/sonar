package de.sonar.sonar.mqtt.example;

import de.sonar.sonar.mqtt.base.reply.RegisterResponderMqttConfig;
import de.sonar.sonar.mqtt.base.reply.AbstractMqttResponderService;
import de.sonar.sonar.mqtt.base.reply.InvalidResponseStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@RegisterResponderMqttConfig(topic = "hello-world")
public class HelloWorldResponderService extends AbstractMqttResponderService<String, String> {

    @Autowired
    public HelloWorldResponderService(MessageChannel mqttReplyOutboundChannel) {
        super(mqttReplyOutboundChannel);
    }

    @Override
    protected String processRequestPayload(String payload) {
        if (!payload.equals("Hello World!")) {
            throw new InvalidResponseStateException("You should say 'Hello World!'!");
        }
        return "Hello World You Too!";
    }
}
