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

    @Override
    public void afterPropertiesSet() {
        RegisterRequestMqttConfig config =
                this.getClass().getAnnotation(RegisterRequestMqttConfig.class);

        if (config != null) {
            this.topic = config.topic();
        } else {
            throw new IllegalStateException(
                    "Service is missing @RegisterRequesterMqttConfig annotation: " + this.getClass().getName());
        }
    }

    public ResponseType sendRequest(RequestType request) {
        UUID requestId = UUID.randomUUID();
        CompletableFuture<ResponseType> future = new CompletableFuture<>();
        pendingRequests.put(requestId, future);

        MqttRequest<RequestType> mqttRequest = new MqttRequest<>(
                request,
                "request/topic/" + getTopic(),
                "reply/topic/" + getTopic(),
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
            Object rawResponsePayload = mqttReply.getPayload();
            if (rawResponsePayload == null) {
                throw new InvalidReplyStateException("MqttResponse has no attribute payload.");
            }
            JavaType responsePayloadType = TypeFactory.defaultInstance()
                    .constructType(((ParameterizedType) getClass().getGenericSuperclass())
                            .getActualTypeArguments()[1]);
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseType responsePayload = objectMapper.convertValue(rawResponsePayload, responsePayloadType);
            pendingRequests.get(requestId).complete(responsePayload);
        } catch (ClassCastException e) {
            throw new InvalidReplyStateException("Failed to convert mqtt response payload");
        }
    }

}
