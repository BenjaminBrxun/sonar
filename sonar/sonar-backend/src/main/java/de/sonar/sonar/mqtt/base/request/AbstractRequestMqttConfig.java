package de.sonar.sonar.mqtt.base.request;

import de.sonar.sonar.mqtt.base.JsonPahoMessageConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * Abstract configuration class for MQTT request handling.
 * <p>
 * Provides base configuration for MQTT message-driven channel adapters
 * to handle request-reply patterns in MQTT communication.
 * <p>
 * This class is not intended for manual use!
 * It should only be used due to the automatic topic registration with automatically
 * registered {@link DynamicRequestMqttConfig} by the {@link MqttRequestServiceRegistrar}.
 *
 * @see MqttRequestServiceRegistrar
 * @see DynamicRequestMqttConfig
 * @see RegisterRequestMqttConfig
 * @see AbstractMqttRequestService
 */
public abstract class AbstractRequestMqttConfig {

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
    @Getter
    @Setter
    private String topic;

    // 1. Outbound: Request

    /**
     * Creates a message channel for outgoing MQTT requests.
     *
     * @return A DirectChannel instance for handling outbound MQTT request messages
     */
    @Bean
    public MessageChannel mqttRequestOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * Configures the integration flow for outgoing MQTT requests.
     *
     * @param mqttRequestOutbound The message handler for outgoing requests
     * @return An IntegrationFlow instance that routes messages to the outbound handler
     */
    @Bean
    public IntegrationFlow mqttRequestOutboundFlow(MessageHandler mqttRequestOutbound) {
        return IntegrationFlow.from(mqttRequestOutboundChannel())
                .handle(mqttRequestOutbound)
                .get();
    }

    /**
     * Creates and configures a message handler for outgoing MQTT requests.
     *
     * @param factory   The MQTT client factory to create MQTT clients
     * @param converter The JSON converter for MQTT message conversion
     * @return A configured MqttPahoMessageHandler for sending requests
     */
    @Bean
    public MessageHandler mqttRequestOutbound(
            MqttPahoClientFactory factory,
            JsonPahoMessageConverter converter
    ) {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(applicationName + "_requestPublisherClient_" + getTopic(), factory);
        handler.setAsync(true);
        handler.setDefaultTopic("request/topic/" + getTopic());
        handler.setConverter(converter);
        return handler;
    }

    // 4. Inbound: Response

    /**
     * Creates a message channel for incoming MQTT replies.
     *
     * @return A DirectChannel instance for handling inbound MQTT reply messages
     */
    @Bean
    public MessageChannel mqttReplyInboundChannel() {
        return new DirectChannel();
    }

    /**
     * Configures the integration flow for incoming MQTT replies.
     *
     * @param mqttReplyInbound The MQTT message-driven channel adapter for incoming replies
     * @return An IntegrationFlow instance that routes messages from the adapter to the inbound channel
     */
    @Bean
    public IntegrationFlow mqttReplyInboundFlow(MqttPahoMessageDrivenChannelAdapter mqttReplyInbound) {
        return IntegrationFlow.from(mqttReplyInbound)
                .channel(mqttReplyInboundChannel())
                .get();
    }

    /**
     * Creates and configures a message-driven channel adapter for incoming MQTT replies.
     *
     * @param factory   The MQTT client factory to create MQTT clients
     * @param converter The JSON converter for MQTT message conversion
     * @return A configured MqttPahoMessageDrivenChannelAdapter for handling incoming replies
     */
    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttReplyInbound(
            MqttPahoClientFactory factory,
            JsonPahoMessageConverter converter) {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        applicationName + "_responseReceiverClient_" + getTopic(), factory, "reply/topic/" + getTopic());
        adapter.setConverter(converter);
        return adapter;
    }
}