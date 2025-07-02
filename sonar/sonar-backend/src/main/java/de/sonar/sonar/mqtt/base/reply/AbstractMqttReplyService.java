package de.sonar.sonar.mqtt.base.reply;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import de.sonar.sonar.mqtt.base.request.InvalidRequestStateException;
import de.sonar.sonar.mqtt.base.request.MqttRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.lang.reflect.ParameterizedType;
import java.util.UUID;

/**
 * Abstract base class for services that handle incoming requests and send replies.
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMqttReplyService<RequestType, ReplyType> {

    private final MessageChannel mqttReplyOutboundChannel;

    private final ObjectMapper objectMapper;

    /**
     * Handles incoming MQTT request messages by processing the {@link MqttRequest} payload
     * and sending a {@link MqttReply}.
     *
     * @param message the incoming MQTT request message containing the {@link MqttRequest}
     * @throws InvalidRequestStateException if the message is invalid or processing fails
     */
    public void handleRequest(Message<MqttRequest<RequestType>> message) {
        validateMessage(message);
        MqttRequest<RequestType> mqttRequest = message.getPayload();
        validateRequestId(mqttRequest);

        RequestType requestPayload = deserializeRequestPayload(mqttRequest);
        ReplyType replyPayload = processRequestPayload(requestPayload);
        sendReply(mqttRequest.getRequestId(), replyPayload);
    }

    /**
     * Validates that the incoming message is not null.
     *
     * @param message the message to validate
     * @throws InvalidRequestStateException if the message is null
     */
    private void validateMessage(Message<MqttRequest<RequestType>> message) {
        if (message == null) {
            throw new InvalidRequestStateException("Message cannot be null");
        }
    }

    /**
     * Validates that the request contains a valid request ID.
     *
     * @param request the MQTT request to validate
     * @throws InvalidRequestStateException if the request ID is null
     */
    private void validateRequestId(MqttRequest<RequestType> request) {
        if (request.getRequestId() == null) {
            throw new InvalidRequestStateException("Request ID cannot be null");
        }
    }

    /**
     * Deserializes the request payload from the MQTT request.
     *
     * @param mqttRequest the MQTT request containing the payload
     * @return the deserialized request payload
     * @throws InvalidRequestStateException if deserialization fails
     */
    private RequestType deserializeRequestPayload(MqttRequest<RequestType> mqttRequest) {
        Object rawPayload = extractRawPayload(mqttRequest);
        JavaType payloadType = determineRequestPayloadType();

        try {
            return objectMapper.convertValue(rawPayload, payloadType);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestStateException("Failed to deserialize request payload.", e);
        }
    }

    /**
     * Extracts and validates the raw payload from the MQTT request.
     *
     * @param mqttRequest the MQTT request containing the payload
     * @return the raw payload object
     * @throws InvalidRequestStateException if the payload is null
     */
    private Object extractRawPayload(MqttRequest<RequestType> mqttRequest) {
        Object rawPayload = mqttRequest.getPayload();
        if (rawPayload == null) {
            throw new InvalidRequestStateException("Request payload cannot be null");
        }
        return rawPayload;
    }

    /**
     * Determines the Java type of the request payload using reflection.
     *
     * @return the JavaType representing the request payload type
     */
    private JavaType determineRequestPayloadType() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        return TypeFactory.defaultInstance()
                .constructType(genericSuperclass.getActualTypeArguments()[0]);
    }

    /**
     * Creates and sends a reply message through the MQTT reply channel.
     *
     * @param requestId    the ID of the original request
     * @param replyPayload the payload to be sent in the reply
     */
    private void sendReply(UUID requestId, ReplyType replyPayload) {
        MqttReply<ReplyType> mqttReply = new MqttReply<>(replyPayload, requestId);
        Message<MqttReply<ReplyType>> replyMessage = new GenericMessage<>(mqttReply);
        mqttReplyOutboundChannel.send(replyMessage);
    }

    /**
     * Process the {@link MqttRequest} payload and forwards a reply.
     * <p>
     * This method must be implemented by concrete classes to define the business logic
     * for handling requests.
     *
     * @param payload the {@link MqttRequest} payload to process
     * @return the reply to be sent back to the requester
     */
    protected abstract ReplyType processRequestPayload(RequestType payload);

}
