package de.sonar.sonar.mqtt.base.request;

import de.sonar.sonar.mqtt.base.reply.MqttReply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents a generic MQTT request message.
 * <p>
 * This class is used to wrap request payload data with request-specific metadata.
 * It is designed to work in conjunction with {@link MqttReply} to handle request-reply patterns over MQTT.
 *
 * @param <RequestType> The type of the payload data for this request
 * @see MqttReply
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MqttRequest<RequestType> {

    /**
     * The type of the message, always set to "REQUEST"
     * <p>
     * Indicates the message type for JSON deserialisation.
     */
    private final String messageType = "REQUEST";

    /**
     * The payload data of the request
     */
    private RequestType payload;

    /**
     * Unique identifier for this request
     */
    private UUID requestId;
}
