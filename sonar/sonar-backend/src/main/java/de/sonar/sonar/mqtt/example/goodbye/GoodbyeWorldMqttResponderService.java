package de.sonar.sonar.mqtt.example.goodbye;

import de.sonar.sonar.mqtt.base.AbstractMqttResponderService;
import de.sonar.sonar.mqtt.base.InvalidResponseStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class GoodbyeWorldMqttResponderService extends AbstractMqttResponderService<String, String> {

    @Autowired
    public GoodbyeWorldMqttResponderService(MessageChannel mqttReplyOutboundChannel) {
        super(mqttReplyOutboundChannel);
    }

    @Override
    protected String processRequestPayload(String payload) {
        if (!payload.equals("Goodbye World!")) {
            throw new InvalidResponseStateException("You should say 'Goodbye World!'!");
        }
        return "Goodbye World You Too!";
    }
}