package de.sonar.hernebackend.mqtt.example;

import de.sonar.hernebackend.mqtt.base.config.RegisterResponderMqttConfig;
import de.sonar.hernebackend.mqtt.base.service.AbstractMqttResponderService;
import de.sonar.hernebackend.mqtt.base.service.InvalidResponseStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
@RegisterResponderMqttConfig(topic = "goodbye-world")
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