package de.sonar.sonar.mqtt.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.sonar.sonar.mqtt.base.reply.MqttReply;
import de.sonar.sonar.mqtt.base.request.InvalidRequestStateException;
import de.sonar.sonar.mqtt.base.request.MqttRequest;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * A custom MQTT message converter that extends {@link DefaultPahoMessageConverter} to handle JSON serialization
 * and deserialization of MQTT messages.
 * <p>
 * This converter specifically handles {@link MqttRequest} and {@link MqttReply}
 * objects, converting them to and from JSON format.
 */
@Slf4j
public class JsonPahoMessageConverter extends DefaultPahoMessageConverter {

    private final ObjectMapper objectMapper;

    public JsonPahoMessageConverter(ObjectMapper objectMapper) {
        this.setPayloadAsBytes(true);
        this.objectMapper = objectMapper;
    }

    /**
     * Converts a Spring Message object to MQTT bytes.
     * <p>
     * If the payload is an instance of {@link MqttRequest} or {@link MqttReply}, it will be serialized to JSON.
     * Otherwise, it delegates to the default implementation.
     *
     * @param message the Spring Message to convert
     * @return the resulting byte array
     * @throws InvalidRequestStateException if the payload cannot be serialized to JSON
     */
    @Override
    protected byte[] messageToMqttBytes(Message<?> message) {
        Object payload = message.getPayload();
        if (!(payload instanceof MqttRequest<?>) && !(payload instanceof MqttReply<?>)) {
            return super.messageToMqttBytes(message);
        }
        try {
            String json = objectMapper.writeValueAsString(payload);
            return json.getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException ex) {
            throw new InvalidRequestStateException("Request payload could not be serialized to JSON.");
        }
    }

    /**
     * Converts MQTT message bytes to an object payload.
     * <p>
     * Attempts to deserialize the JSON content into either an {@link MqttRequest} or {@link MqttReply}
     * based on the message type field in the JSON.
     * Otherwise, it delegates to the default implementation.
     *
     * @param mqttMessage the MQTT message to convert
     * @return the deserialized object (either {@link MqttRequest} or {@link MqttReply})
     * @throws InvalidRequestStateException if the message type is unknown
     */
    @Override
    protected Object mqttBytesToPayload(MqttMessage mqttMessage) {
        try {
            String json = new String(mqttMessage.getPayload(), StandardCharsets.UTF_8);
            String messageType = extractMessageType(json);

            if ("REQUEST".equalsIgnoreCase(messageType)) {
                return objectMapper.readValue(json, MqttRequest.class);
            } else if ("REPLY".equalsIgnoreCase(messageType)) {
                return objectMapper.readValue(json, MqttReply.class);
            } else {
                throw new InvalidRequestStateException("Unknown message type in MQTT payload.");
            }

        } catch (IOException e) {
            log.error("Could not deserialize MQTT message. Try to deserialize with default implementation.", e);
            return super.mqttBytesToPayload(mqttMessage);
        }
    }

    /**
     * Extracts the message type from the JSON string.
     * <p>
     * Should return either REQUEST or REPLY.
     *
     * @param json the JSON string to read
     * @return the message type as a string
     * @throws IOException if the JSON cannot be parsed or does not contain a messageType field
     */
    private String extractMessageType(String json) throws IOException {
        return objectMapper.readTree(json).path("messageType").asText();
    }

}
