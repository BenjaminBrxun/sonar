package de.sonar.hernebackend.mqtt.impl.request;

import de.sonar.hernebackend.mqtt.base.JsonPahoMessageConverter;
import de.sonar.hernebackend.mqtt.base.request.AbstractRequestMqttConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class EventDeletionRequestMqttConfig extends AbstractRequestMqttConfig {

    @Override
    protected String getTopic() {
        return "delete-event";
    }

    // 1. Outbound: Request
    @Bean("delete-event_mqttRequestOutboundChannel")
    @Override
    public MessageChannel mqttRequestOutboundChannel() {
        return super.mqttRequestOutboundChannel();
    }

    @Bean("delete-event_mqttRequestOutboundFlow")
    @Override
    public IntegrationFlow mqttRequestOutboundFlow(
            @Qualifier("delete-event_mqttRequestOutbound") MessageHandler mqttRequestOutbound) {
        return super.mqttRequestOutboundFlow(mqttRequestOutbound);
    }

    @Bean("delete-event_mqttRequestOutbound")
    @Override
    public MessageHandler mqttRequestOutbound(
            MqttPahoClientFactory factory,
            JsonPahoMessageConverter converter) {
        return super.mqttRequestOutbound(factory, converter);
    }

    // 4. Inbound: Response
    @Bean("delete-event_mqttReplyInboundChannel")
    @Override
    public MessageChannel mqttReplyInboundChannel() {
        return super.mqttReplyInboundChannel();
    }

    @Bean("delete-event_mqttReplyInboundFlow")
    @Override
    public IntegrationFlow mqttReplyInboundFlow(
            @Qualifier("delete-event_mqttReplyInbound") MqttPahoMessageDrivenChannelAdapter mqttReplyInbound) {
        return super.mqttReplyInboundFlow(mqttReplyInbound);
    }

    @Bean("delete-event_mqttReplyInbound")
    @Override
    public MqttPahoMessageDrivenChannelAdapter mqttReplyInbound(
            MqttPahoClientFactory factory,
            JsonPahoMessageConverter converter) {
        return super.mqttReplyInbound(factory, converter);
    }

}
