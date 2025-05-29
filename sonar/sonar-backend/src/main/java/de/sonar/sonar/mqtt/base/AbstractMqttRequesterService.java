package de.sonar.sonar.mqtt.base;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMqttRequesterService<RequestType, ResponseType> {

    @Value("${mqtt.communication.timeout.millis}")
    private long timeoutMillis;

    private final Map<UUID, CompletableFuture<ResponseType>> pendingRequests = new HashMap<>();

    private final MessageChannel mqttRequestOutboundChannel;

    public ResponseType sendRequest(RequestType request) {
        UUID requestId = UUID.randomUUID();
        CompletableFuture<ResponseType> future = new CompletableFuture<>();
        pendingRequests.put(requestId, future);

        MqttRequest<RequestType> mqttRequest = new MqttRequest<>(
                request,
                "request/topic",
                "reply/topic",
                requestId
        );

        Message<MqttRequest<RequestType>> message = new GenericMessage<>(mqttRequest);

        mqttRequestOutboundChannel.send(message);

        try {
            return future.get(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new InvalidResponseStateException("Timeout while waiting for response.");
        } finally {
            pendingRequests.remove(requestId);
        }
    }


    @ServiceActivator(inputChannel = "mqttReplyInboundChannel")
    public void handleResponse(Message<MqttResponse<ResponseType>> message) {
        if (message == null) {
            throw new InvalidResponseStateException("Message is null.");
        }

        MqttResponse<ResponseType> mqttResponse = message.getPayload();
        UUID requestId = mqttResponse.getRequestId();
        if (requestId == null) {
            throw new InvalidResponseStateException("Message has no request_id.");
        }

        if (!pendingRequests.containsKey(requestId)) {
            throw new InvalidResponseStateException("No pending request for request_id: " + requestId);
        }

        ResponseType response = mqttResponse.getPayload();
        pendingRequests.get(requestId).complete(response);
    }

}
