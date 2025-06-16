package de.sonar.sonar.mqtt.base.reply;

import de.sonar.sonar.mqtt.base.request.MqttRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents a generic MQTT reply message.
 * <p>
 * This class is used to wrap reply payload data with reply-specific metadata.
 * It is designed to work in conjunction with {@link MqttRequest} to handle request-reply patterns over MQTT.
 *
 * @param <ReplyType> The type of the payload data for this reply
 * @see MqttRequest
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MqttReply<ReplyType> {

    /**
     * The type of the message, always set to "REPLY"
     * <p>
     * Indicates the message type for JSON deserialisation.
     */
    private final String messageType = "REPLY";

    /**
     * The payload data of the reply
     */
    private ReplyType payload;

    /**
     * The ID of the related {@link MqttRequest}
     */
    private UUID requestId;
}
