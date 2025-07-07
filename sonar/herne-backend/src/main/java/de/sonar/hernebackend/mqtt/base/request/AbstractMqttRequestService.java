package de.sonar.hernebackend.mqtt.base.request;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import de.sonar.hernebackend.mqtt.base.reply.InvalidReplyStateException;
import de.sonar.hernebackend.mqtt.base.reply.MqttReply;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Abstract base class for services that handle send requests and handle incoming replies.
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMqttRequestService<RequestType, ReplyType> {

    @Value("${mqtt.communication.timeout.millis}")
    private long timeoutMillis;

    private final Map<UUID, CompletableFuture<ReplyType>> pendingRequests = new HashMap<>();

    private final MessageChannel mqttRequestOutboundChannel;

    private final ObjectMapper objectMapper;

    /**
     * Sends a request via MQTT and waits for a reply within a specified timeout.
     *
     * @param request the payload of the request to be sent
     * @return the reply received within the timeout period
     * @throws InvalidReplyStateException if the reply is not received in time or any error occurs during processing
     */
    public ReplyType sendRequest(RequestType request) {
        UUID requestId = UUID.randomUUID();
        CompletableFuture<ReplyType> future = registerPendingRequest(requestId);
        sendMqttRequest(request, requestId);
        return waitForResponse(requestId, future);
    }

    /**
     * Registers a new pending request with the given ID.
     *
     * @param requestId the UUID of the request
     * @return a CompletableFuture for the reply
     */
    private CompletableFuture<ReplyType> registerPendingRequest(UUID requestId) {
        CompletableFuture<ReplyType> future = new CompletableFuture<>();
        pendingRequests.put(requestId, future);
        return future;
    }

    /**
     * Sends the MQTT request message.
     *
     * @param request   the request payload
     * @param requestId the UUID of the request
     */
    private void sendMqttRequest(RequestType request, UUID requestId) {
        MqttRequest<RequestType> mqttRequest = new MqttRequest<>(request, requestId);
        Message<MqttRequest<RequestType>> message = new GenericMessage<>(mqttRequest);
        mqttRequestOutboundChannel.send(message);
    }

    /**
     * Waits for the response within the configured timeout period.
     *
     * @param requestId the UUID of the request
     * @param future    the CompletableFuture to wait on
     * @return the reply payload
     * @throws InvalidReplyStateException if the timeout is exceeded
     */
    private ReplyType waitForResponse(UUID requestId, CompletableFuture<ReplyType> future) {
        try {
            return future.get(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new InvalidReplyStateException("Timeout while waiting for response");
        } finally {
            pendingRequests.remove(requestId);
        }
    }

    /**
     * Handles incoming MQTT reply messages and processes the response based on the request ID.
     * <p>
     * This method extracts the request ID from the message, verifies its existence in the pending requests,
     * deserializes the payload, and completes the corresponding request's CompletableFuture.
     *
     * @param message the incoming MQTT reply message containing the {@link MqttReply}
     * @throws InvalidReplyStateException if the message is null, lacks a request ID,
     *                                    has no payload, or if the payload conversion fails
     */
    public void handleResponse(Message<MqttReply<ReplyType>> message) {
        validateReplyMessage(message);
        MqttReply<ReplyType> mqttReply = message.getPayload();
        UUID requestId = validateAndExtractRequestId(mqttReply);
        validatePendingRequest(requestId);

        ReplyType replyPayload = deserializeReplyPayload(mqttReply);
        completePendingRequest(requestId, replyPayload);
    }

    /**
     * Validates that the reply message is not null.
     *
     * @param message the message to validate
     * @throws InvalidReplyStateException if the message is null
     */
    private void validateReplyMessage(Message<MqttReply<ReplyType>> message) {
        if (message == null) {
            throw new InvalidReplyStateException("Reply message cannot be null");
        }
    }

    /**
     * Validates and extracts the request ID from the reply.
     *
     * @param mqttReply the MQTT reply
     * @return the validated request ID
     * @throws InvalidReplyStateException if the request ID is null
     */
    private UUID validateAndExtractRequestId(MqttReply<ReplyType> mqttReply) {
        UUID requestId = mqttReply.getRequestId();
        if (requestId == null) {
            throw new InvalidReplyStateException("Reply has no request ID");
        }
        return requestId;
    }

    /**
     * Validates that a pending request exists for the given ID.
     *
     * @param requestId the request ID to validate
     * @throws InvalidReplyStateException if no pending request exists
     */
    private void validatePendingRequest(UUID requestId) {
        if (!pendingRequests.containsKey(requestId)) {
            throw new InvalidReplyStateException("No pending request found for ID: " + requestId);
        }
    }

    /**
     * Deserializes the reply payload.
     *
     * @param mqttReply the MQTT reply containing the payload
     * @return the deserialized reply payload
     * @throws InvalidReplyStateException if deserialization fails
     */
    private ReplyType deserializeReplyPayload(MqttReply<ReplyType> mqttReply) {
        Object rawPayload = extractRawPayload(mqttReply);
        JavaType replyType = determineReplyPayloadType();

        try {
            return objectMapper.convertValue(rawPayload, replyType);
        } catch (IllegalArgumentException e) {
            throw new InvalidReplyStateException("Failed to deserialize reply payload", e);
        }
    }

    /**
     * Extracts the raw payload from the MQTT reply.
     *
     * @param mqttReply the MQTT reply
     * @return the raw payload object
     * @throws InvalidReplyStateException if the payload is null
     */
    private Object extractRawPayload(MqttReply<ReplyType> mqttReply) {
        Object rawPayload = mqttReply.getPayload();
        if (rawPayload == null) {
            throw new InvalidReplyStateException("Reply payload cannot be null");
        }
        return rawPayload;
    }

    /**
     * Determines the Java type of the reply payload using reflection.
     *
     * @return the JavaType representing the reply payload type
     */
    private JavaType determineReplyPayloadType() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        return TypeFactory.defaultInstance()
                .constructType(genericSuperclass.getActualTypeArguments()[1]);
    }

    /**
     * Completes a pending request with the received reply payload.
     *
     * @param requestId    the ID of the request to complete
     * @param replyPayload the reply payload
     */
    private void completePendingRequest(UUID requestId, ReplyType replyPayload) {
        pendingRequests.get(requestId).complete(replyPayload);
    }


}
