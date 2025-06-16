package de.sonar.sonar.mqtt.base.reply;

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
 * Abstract configuration class for MQTT reply handling.
 * <p>
 * Provides base configuration for MQTT message-driven channel adapters
 * to handle request-reply patterns in MQTT communication.
 * <p>
 * This class is not intended for manual use!
 * It should only be used due to the automatic topic registration with automatically
 * registered {@link DynamicReplyMqttConfig} by the {@link MqttReplyServiceRegistrar}.
 *
 * @see MqttReplyServiceRegistrar
 * @see DynamicReplyMqttConfig
 * @see RegisterReplyMqttConfig
 * @see AbstractMqttReplyService
 */
public abstract class AbstractReplyMqttConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * The MQTT topic to be used for this configuration
     */
    @Getter
    @Setter
    private String topic;

    // 2. Inbound: Request

    @Bean
    public MessageChannel mqttRequestInboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow mqttRequestInboundFlow(
            MqttPahoMessageDrivenChannelAdapter mqttRequestInbound) {
        return IntegrationFlow.from(mqttRequestInbound)
                .channel(mqttRequestInboundChannel())
                .get();
    }

    @Bean
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

    @Bean
    public MessageChannel mqttReplyOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow mqttReplyOutboundFlow(MessageHandler mqttReplyOutbound) {
        return IntegrationFlow.from(mqttReplyOutboundChannel())
                .handle(mqttReplyOutbound)
                .get();
    }

    @Bean
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