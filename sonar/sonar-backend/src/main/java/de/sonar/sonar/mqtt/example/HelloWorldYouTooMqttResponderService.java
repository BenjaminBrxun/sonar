package de.sonar.sonar.mqtt.example;

import de.sonar.sonar.mqtt.base.AbstractMqttResponderService;
import de.sonar.sonar.mqtt.base.InvalidResponseStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldYouTooMqttResponderService extends AbstractMqttResponderService<String, String> {

    @Autowired
    public HelloWorldYouTooMqttResponderService(MessageChannel mqttReplyOutboundChannel) {
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
