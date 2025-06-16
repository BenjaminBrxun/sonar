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

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * The MQTT topic to be used for this configuration
     */
    @Getter
    @Setter
    private String topic;

    // 1. Outbound: Request

    @Bean
    public MessageChannel mqttRequestOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow mqttRequestOutboundFlow(MessageHandler mqttRequestOutbound) {
        return IntegrationFlow.from(mqttRequestOutboundChannel())
                .handle(mqttRequestOutbound)
                .get();
    }

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

    @Bean
    public MessageChannel mqttReplyInboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow mqttReplyInboundFlow(MqttPahoMessageDrivenChannelAdapter mqttReplyInbound) {
        return IntegrationFlow.from(mqttReplyInbound)
                .channel(mqttReplyInboundChannel())
                .get();
    }

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