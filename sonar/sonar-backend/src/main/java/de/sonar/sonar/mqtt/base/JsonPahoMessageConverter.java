package de.sonar.sonar.mqtt.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.sonar.sonar.mqtt.base.request.InvalidRequestStateException;
import de.sonar.sonar.mqtt.base.request.MqttRequest;
import de.sonar.sonar.mqtt.base.reply.MqttResponse;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JsonPahoMessageConverter extends DefaultPahoMessageConverter {

    private final ObjectMapper objectMapper;

    public JsonPahoMessageConverter() {
        this.setPayloadAsBytes(true);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected byte[] messageToMqttBytes(Message<?> message) {
        Object payload = message.getPayload();
        if (!(payload instanceof MqttRequest<?>) && !(payload instanceof MqttResponse<?>)) {
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
        try {
            String json = new String(mqttMessage.getPayload(), StandardCharsets.UTF_8);
            String messageType = extractMessageType(json);

            if ("REQUEST".equalsIgnoreCase(messageType)) {
                return objectMapper.readValue(json, MqttRequest.class);
            } else if ("RESPONSE".equalsIgnoreCase(messageType)) {
                return objectMapper.readValue(json, MqttResponse.class);
            } else {
                throw new InvalidRequestStateException("Unknown message type in MQTT payload.");
            }

        } catch (IOException e) {
            log.error("Could not deserialize MQTT message. Try to deserialize with default implementation.", e);
            return super.mqttBytesToPayload(mqttMessage);
        }
    }

    private String extractMessageType(String json) throws IOException {
        return objectMapper.readTree(json).path("messageType").asText();
    }

}
