package de.sonar.sonar.mqtt.base.example;

import de.sonar.sonar.mqtt.base.JsonPahoMessageConverter;
import de.sonar.sonar.mqtt.base.request.AbstractRequestMqttConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class HelloWorldRequestMqttConfig extends AbstractRequestMqttConfig {

    @Override
    protected String getTopic() {
        return "hello-world";
    }

    // 1. Outbound: Request
    @Bean("hello-world_mqttRequestOutboundChannel")
    @Override
    public MessageChannel mqttRequestOutboundChannel() {
        return super.mqttRequestOutboundChannel();
    }

    @Bean("hello-world_mqttRequestOutboundFlow")
    @Override
    public IntegrationFlow mqttRequestOutboundFlow(
            @Qualifier("hello-world_mqttRequestOutbound") MessageHandler mqttRequestOutbound) {
        return super.mqttRequestOutboundFlow(mqttRequestOutbound);
    }

    @Bean("hello-world_mqttRequestOutbound")
    @Override
    public MessageHandler mqttRequestOutbound(
            MqttPahoClientFactory factory,
            JsonPahoMessageConverter converter) {
        return super.mqttRequestOutbound(factory, converter);
    }

    // 4. Inbound: Response
    @Bean("hello-world_mqttReplyInboundChannel")
    @Override
    public MessageChannel mqttReplyInboundChannel() {
        return super.mqttReplyInboundChannel();
    }

    @Bean("hello-world_mqttReplyInboundFlow")
    @Override
    public IntegrationFlow mqttReplyInboundFlow(
            @Qualifier("hello-world_mqttReplyInbound") MqttPahoMessageDrivenChannelAdapter mqttReplyInbound) {
        return super.mqttReplyInboundFlow(mqttReplyInbound);
    }

    @Bean("hello-world_mqttReplyInbound")
    @Override
    public MqttPahoMessageDrivenChannelAdapter mqttReplyInbound(
            MqttPahoClientFactory factory,
            JsonPahoMessageConverter converter) {
        return super.mqttReplyInbound(factory, converter);
    }


}
