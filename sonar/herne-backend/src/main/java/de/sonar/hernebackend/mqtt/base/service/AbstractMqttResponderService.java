package de.sonar.hernebackend.mqtt.base.service;

import de.sonar.hernebackend.mqtt.base.config.RegisterResponderMqttConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMqttResponderService<RequestType, ResponseType> implements InitializingBean {

    private final MessageChannel mqttReplyOutboundChannel;

    @Override
    public void afterPropertiesSet() {
        RegisterResponderMqttConfig config =
                this.getClass().getAnnotation(RegisterResponderMqttConfig.class);

        if (config == null) {
            throw new IllegalStateException(
                    "Service is missing @RegisterResponderMqttConfig annotation: " + this.getClass().getName());
        }

    }

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

        try {
            log.info("Try to cast payload to ResponseType and send response to topic:");
            Message<MqttResponse<ResponseType>> responseMessage = new GenericMessage<>(
                    new MqttResponse<>(
                            response,
                            (Class<ResponseType>) response.getClass(),
                            responseTopic,
                            requestId
                    ));
            mqttReplyOutboundChannel.send(responseMessage);
        } catch (Exception e) {
            log.error("Could not send response to MQTT topic: {}", responseTopic, e);
        }
    }

    protected abstract ResponseType processRequestPayload(RequestType payload);

}
