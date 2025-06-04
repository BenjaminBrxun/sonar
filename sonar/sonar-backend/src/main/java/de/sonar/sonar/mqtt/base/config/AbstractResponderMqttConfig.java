package de.sonar.sonar.mqtt.base.config;

import de.sonar.sonar.mqtt.base.JsonPahoMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public abstract class AbstractResponderMqttConfig {

    protected abstract String getTopic();

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
                        "requestReceiverClient_" + getTopic(), factory, "request/topic/" + getTopic());
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
                "responsePublisherClient_" + getTopic(), factory);
        handler.setAsync(true);
        handler.setDefaultTopic("reply/topic/" + getTopic());
        handler.setConverter(converter);
        return handler;
    }
}