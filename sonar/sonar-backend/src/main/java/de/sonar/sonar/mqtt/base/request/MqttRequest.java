package de.sonar.sonar.mqtt.base.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents a generic MQTT request message.
 * This class is used to wrap payload data with request-specific metadata.
 *
 * @param <RequestType> The type of the payload data for this request
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MqttRequest<RequestType> {

    /**
     * The type of the message, always set to "REQUEST"
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
