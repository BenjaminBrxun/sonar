package de.sonar.sonar.mqtt.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JsonPahoMessageConverter extends DefaultPahoMessageConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected byte[] messageToMqttBytes(Message<?> message) {
        Object payload = message.getPayload();
        if (!(payload instanceof MqttRequest<?>)) {
            return super.messageToMqttBytes(message);
        }
        try {
            String json = objectMapper.writeValueAsString(payload);
            return json.getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException ex) {
            throw new InvalidRequestStateException("Request payload could not be serialized to JSON.");
        }
    }

    @Override
    protected Object mqttBytesToPayload(MqttMessage mqttMessage) {
        // Todo: Hier ist ein Fehler, es kommt aber schon was an. :)
        log.info("Received message: {}", mqttMessage.toString());
        try {
            return objectMapper.readValue(mqttMessage.toString(), MqttRequest.class);
        } catch (IOException e) {
            log.error("Could not deserialize MQTT message.", e);
            return super.mqttBytesToPayload(mqttMessage);
        }
    }

}
