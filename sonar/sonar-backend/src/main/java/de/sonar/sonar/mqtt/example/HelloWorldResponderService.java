package de.sonar.sonar.mqtt.example;

import de.sonar.sonar.mqtt.base.config.RegisterResponderMqttConfig;
import de.sonar.sonar.mqtt.base.service.AbstractMqttResponderService;
import de.sonar.sonar.mqtt.base.service.InvalidResponseStateException;
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
