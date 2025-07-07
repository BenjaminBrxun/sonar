package de.sonar.sonar.mqtt.impl.reply;

import de.sonar.sonar.mqtt.base.JsonPahoMessageConverter;
import de.sonar.sonar.mqtt.base.reply.AbstractReplyMqttConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class EventCancellationReplyMqttConfig extends AbstractReplyMqttConfig {

    @Override
    protected String getTopic() {
        return "cancel-event";
    }

    // 2. Inbound: Request

    @Bean("cancel-event_mqttRequestInboundChannel")
    @Override
    public MessageChannel mqttRequestInboundChannel() {
        return super.mqttRequestInboundChannel();
    }

    @Bean("cancel-event_mqttRequestInboundFlow")
    @Override
    public IntegrationFlow mqttRequestInboundFlow(
            @Qualifier("cancel-event_mqttRequestInbound") MqttPahoMessageDrivenChannelAdapter mqttRequestInbound) {
        return super.mqttRequestInboundFlow(mqttRequestInbound);
    }

    @Bean("cancel-event_mqttRequestInbound")
    @Override
    public MqttPahoMessageDrivenChannelAdapter mqttRequestInbound(
            MqttPahoClientFactory factory,
            JsonPahoMessageConverter converter) {
        return super.mqttRequestInbound(factory, converter);
    }

    // 3. Outbound: Response

    @Bean("cancel-event_mqttReplyOutboundChannel")
    @Override
    public MessageChannel mqttReplyOutboundChannel() {
        return super.mqttReplyOutboundChannel();
    }

    @Bean("cancel-event_mqttReplyOutboundFlow")
    @Override
    public IntegrationFlow mqttReplyOutboundFlow(
            @Qualifier("cancel-event_mqttReplyOutbound") MessageHandler mqttReplyOutbound) {
        return super.mqttReplyOutboundFlow(mqttReplyOutbound);
    }

    @Bean("cancel-event_mqttReplyOutbound")
    @Override
    public MessageHandler mqttReplyOutbound(
            MqttPahoClientFactory factory,
            JsonPahoMessageConverter converter) {
        return super.mqttReplyOutbound(factory, converter);
    }

}
