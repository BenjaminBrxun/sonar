package de.sonar.hernebackend.mqtt.base.reply;

import de.sonar.hernebackend.mqtt.base.JsonPahoMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * Abstract configuration class for MQTT reply handling.
 * <p>
 * Provides base configuration for MQTT message-driven channel adapters
 * to handle request-reply patterns in MQTT communication.
 *
 * @see AbstractMqttReplyService
 */
public abstract class AbstractReplyMqttConfig {

    /**
     * Represents the name of the application.
     * <p>
     * It is used to construct unique MQTT client identifiers and ensure proper message routing in the MQTT
     * request-reply pattern.
     */
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * The MQTT topic to be used for this configuration
     */
    protected abstract String getTopic();

    // 2. Inbound: Request

    /**
     * Creates a message channel for incoming MQTT requests.
     *
     * @return A DirectChannel instance for handling inbound MQTT request messages
     */
    public MessageChannel mqttRequestInboundChannel() {
        return new DirectChannel();
    }

    /**
     * Configures the integration flow for incoming MQTT requests.
     *
     * @param mqttRequestInbound The MQTT message-driven channel adapter for incoming requests
     * @return An IntegrationFlow instance that routes messages from the adapter to the inbound channel
     */
    public IntegrationFlow mqttRequestInboundFlow(
            MqttPahoMessageDrivenChannelAdapter mqttRequestInbound) {
        return IntegrationFlow.from(mqttRequestInbound)
                .channel(mqttRequestInboundChannel())
                .get();
    }

    /**
     * Creates and configures a message-driven channel adapter for incoming MQTT requests.
     *
     * @param factory   The MQTT client factory to create MQTT clients
     * @param converter The JSON converter for MQTT message conversion
     * @return A configured MqttPahoMessageDrivenChannelAdapter for handling incoming requests
     */
    public MqttPahoMessageDrivenChannelAdapter mqttRequestInbound(
            MqttPahoClientFactory factory,
            JsonPahoMessageConverter converter) {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        applicationName + "_requestReceiverClient_" + getTopic(), factory, "request/topic/" + getTopic());
        adapter.setConverter(converter);
        return adapter;
    }

    // 3. Outbound: Response

    /**
     * Creates a message channel for outgoing MQTT replies.
     *
     * @return A DirectChannel instance for handling outbound MQTT reply messages
     */
    public MessageChannel mqttReplyOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * Configures the integration flow for outgoing MQTT replies.
     *
     * @param mqttReplyOutbound The message handler for outgoing replies
     * @return An IntegrationFlow instance that routes messages to the outbound handler
     */
    public IntegrationFlow mqttReplyOutboundFlow(MessageHandler mqttReplyOutbound) {
        return IntegrationFlow.from(mqttReplyOutboundChannel())
                .handle(mqttReplyOutbound)
                .get();
    }

    /**
     * Creates and configures a message handler for outgoing MQTT replies.
     *
     * @param factory   The MQTT client factory to create MQTT clients
     * @param converter The JSON converter for MQTT message conversion
     * @return A configured MqttPahoMessageHandler for sending replies
     */
    public MessageHandler mqttReplyOutbound(
            MqttPahoClientFactory factory,
            JsonPahoMessageConverter converter) {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
                applicationName + "_responsePublisherClient_" + getTopic(), factory);
        handler.setAsync(true);
        handler.setDefaultTopic("reply/topic/" + getTopic());
        handler.setConverter(converter);
        return handler;
    }
}