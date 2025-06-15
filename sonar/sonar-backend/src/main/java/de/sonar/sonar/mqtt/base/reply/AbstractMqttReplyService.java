package de.sonar.sonar.mqtt.base.reply;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import de.sonar.sonar.mqtt.base.request.InvalidRequestStateException;
import de.sonar.sonar.mqtt.base.request.MqttRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.lang.reflect.ParameterizedType;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMqttReplyService<RequestType, ResponseType> implements InitializingBean {

    private final MessageChannel mqttReplyOutboundChannel;

    @Override
    public void afterPropertiesSet() {
        RegisterReplyMqttConfig config =
                this.getClass().getAnnotation(RegisterReplyMqttConfig.class);

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

        RequestType requestPayload;
        try {
            Object rawRequestPayload = mqttRequest.getPayload();
            if (rawRequestPayload == null) {
                throw new InvalidRequestStateException("MqttRequest has no attribute payload.");
            }
            JavaType requestPayloadType = TypeFactory.defaultInstance()
                    .constructType(((ParameterizedType) getClass().getGenericSuperclass())
                            .getActualTypeArguments()[0]);
            ObjectMapper objectMapper = new ObjectMapper();
            requestPayload = objectMapper.convertValue(rawRequestPayload, requestPayloadType);
        } catch (ClassCastException e) {
            throw new InvalidRequestStateException("Failed to convert mqtt request payload.");
        }

        ResponseType response = processRequestPayload(requestPayload);

        Message<MqttReply<ResponseType>> responseMessage = new GenericMessage<>(
                new MqttReply<>(
                        response,
                        responseTopic,
                        requestId
                ));
        mqttReplyOutboundChannel.send(responseMessage);
    }

    protected abstract ResponseType processRequestPayload(RequestType payload);

}
