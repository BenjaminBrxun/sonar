package de.sonar.sonar.mqtt.base.request;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import de.sonar.sonar.mqtt.base.reply.InvalidReplyStateException;
import de.sonar.sonar.mqtt.base.reply.MqttReply;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Abstract base class for services that handle send requests and handle incoming replies.
 * <p>
 * Implementations of this class must be annotated by {@link RegisterRequestMqttConfig}
 * to autoconfigure the message channel for a specific topic.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMqttRequestService<RequestType, ResponseType> implements InitializingBean {

    @Getter
    private String topic;

    @Value("${mqtt.communication.timeout.millis}")
    private long timeoutMillis;

    private final Map<UUID, CompletableFuture<ResponseType>> pendingRequests = new HashMap<>();

    private final MessageChannel mqttRequestOutboundChannel;

    /**
     * Validates that the service is properly configured with required annotations.
     *
     * @throws IllegalStateException if the {@link RegisterRequestMqttConfig} annotation is missing
     */
    @Override
    public void afterPropertiesSet() {
        RegisterRequestMqttConfig config =
                this.getClass().getAnnotation(RegisterRequestMqttConfig.class);

        if (config != null) {
            this.topic = config.topic();
        } else {
            throw new IllegalStateException(
                    "Service is missing @RegisterRequestMqttConfig annotation: " + this.getClass().getName());
        }
    }

    public ResponseType sendRequest(RequestType request) {
        UUID requestId = UUID.randomUUID();
        CompletableFuture<ResponseType> future = new CompletableFuture<>();
        pendingRequests.put(requestId, future);

        MqttRequest<RequestType> mqttRequest = new MqttRequest<>(
                request,
                requestId
        );

        Message<MqttRequest<RequestType>> message = new GenericMessage<>(mqttRequest);

        mqttRequestOutboundChannel.send(message);

        try {
            return future.get(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new InvalidReplyStateException("Timeout while waiting for response.");
        } finally {
            pendingRequests.remove(requestId);
        }
    }


    @ServiceActivator(inputChannel = "mqttReplyInboundChannel")
    public void handleResponse(Message<MqttReply<ResponseType>> message) {
        if (message == null) {
            throw new InvalidReplyStateException("Message is null.");
        }

        MqttReply<ResponseType> mqttReply = message.getPayload();
        UUID requestId = mqttReply.getRequestId();
        if (requestId == null) {
            throw new InvalidReplyStateException("Message has no request_id.");
        }

        if (!pendingRequests.containsKey(requestId)) {
            throw new InvalidReplyStateException("No pending request for request_id: " + requestId);
        }


        try {
            Object rawReplyPayload = mqttReply.getPayload();
            if (rawReplyPayload == null) {
                throw new InvalidReplyStateException("MqttReply has no attribute payload.");
            }
            JavaType replyPayloadType = TypeFactory.defaultInstance()
                    .constructType(((ParameterizedType) getClass().getGenericSuperclass())
                            .getActualTypeArguments()[1]);
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseType replyPayload = objectMapper.convertValue(rawReplyPayload, replyPayloadType);
            pendingRequests.get(requestId).complete(replyPayload);
        } catch (ClassCastException e) {
            throw new InvalidReplyStateException("Failed to convert mqtt response payload");
        }
    }

}
