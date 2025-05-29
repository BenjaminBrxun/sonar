package de.sonar.sonar.mqtt.base;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMqttResponderService<RequestType, ResponseType> {

    private final MessageChannel mqttReplyOutboundChannel;

    @ServiceActivator(inputChannel = "mqttRequestInboundChannel")
    public void handleRequest(Message<MqttRequest<RequestType>> message) {
        if (message == null) {
            throw new InvalidRequestStateException("Message is null.");
        }

        MqttRequest<RequestType> mqttRequest = message.getPayload();
        String responseTopic = mqttRequest.getResponseTopic();
        if (responseTopic == null) {
            throw new InvalidRequestStateException("Message has no response_topic.");
        }

        UUID requestId = mqttRequest.getRequestId();
        if (requestId == null) {
            throw new InvalidRequestStateException("Message has no request_id.");
        }

        ResponseType response = processRequestPayload(mqttRequest.getPayload());

        Message<MqttResponse<ResponseType>> responseMessage = new GenericMessage<>(
                new MqttResponse<>(
                        response,
                        responseTopic,
                        requestId
                ));

        mqttReplyOutboundChannel.send(responseMessage);
    }

    protected abstract ResponseType processRequestPayload(RequestType payload);

}
