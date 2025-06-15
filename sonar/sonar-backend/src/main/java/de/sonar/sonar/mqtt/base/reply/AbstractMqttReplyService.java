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

/**
 * Abstract base class for services that handle incoming requests and send replies.
 * <p>
 * Implementations of this class must be annotated by {@link RegisterReplyMqttConfig}
 * to autoconfigure the message channel for a specific topic.
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractMqttReplyService<RequestType, ReplyType> implements InitializingBean {

    private final MessageChannel mqttReplyOutboundChannel;

    /**
     * Validates that the service is properly configured with required annotations.
     *
     * @throws IllegalStateException if the {@link RegisterReplyMqttConfig} annotation is missing
     */
    @Override
    public void afterPropertiesSet() {
        RegisterReplyMqttConfig config =
                this.getClass().getAnnotation(RegisterReplyMqttConfig.class);

        if (config == null) {
            throw new IllegalStateException(
                    "Service is missing @RegisterReplyMqttConfig annotation: " + this.getClass().getName());
        }
    }

    /**
     * Handles incoming MQTT {@link Message}s by processing the {@link MqttRequest} payload
     * and sending a {@link MqttReply}.
     *
     * @param message the incoming MQTT {@link Message} containing the {@link MqttRequest}
     * @throws InvalidRequestStateException if the message is invalid or processing fails
     */
    @ServiceActivator(inputChannel = "mqttRequestInboundChannel")
    public void handleRequest(Message<MqttRequest<RequestType>> message) {
        if (message == null) {
            throw new InvalidRequestStateException("Message is null.");
        }

        MqttRequest<RequestType> mqttRequest = message.getPayload();

        UUID requestId = mqttRequest.getRequestId();
        if (requestId == null) {
            throw new InvalidRequestStateException("Message has no request id.");
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

        ReplyType replyPayload = processRequestPayload(requestPayload);

        Message<MqttReply<ReplyType>> replyMessage = new GenericMessage<>(
                new MqttReply<>(
                        replyPayload,
                        requestId
                ));
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
