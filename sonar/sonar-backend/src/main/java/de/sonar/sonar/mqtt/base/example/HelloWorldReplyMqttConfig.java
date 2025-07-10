package de.sonar.sonar.mqtt.base.example;

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
public class HelloWorldReplyMqttConfig extends AbstractReplyMqttConfig {

    @Override
    protected String getTopic() {
        return "hello-world";
    }

    // 2. Inbound: Request

    @Bean("hello-world_mqttRequestInboundChannel")
    @Override
    public MessageChannel mqttRequestInboundChannel() {
        return super.mqttRequestInboundChannel();
    }

    @Bean("hello-world_mqttRequestInboundFlow")
    @Override
    public IntegrationFlow mqttRequestInboundFlow(
            @Qualifier("hello-world_mqttRequestInbound") MqttPahoMessageDrivenChannelAdapter mqttRequestInbound) {
        return super.mqttRequestInboundFlow(mqttRequestInbound);
    }

    @Bean("hello-world_mqttRequestInbound")
    @Override
    public MqttPahoMessageDrivenChannelAdapter mqttRequestInbound(
            MqttPahoClientFactory factory,
            JsonPahoMessageConverter converter) {
        return super.mqttRequestInbound(factory, converter);
    }

    // 3. Outbound: Response
    @Bean("hello-world_mqttReplyOutboundChannel")
    @Override
    public MessageChannel mqttReplyOutboundChannel() {
        return super.mqttReplyOutboundChannel();
    }

    @Bean("hello-world_mqttReplyOutboundFlow")
    @Override
    public IntegrationFlow mqttReplyOutboundFlow(
            @Qualifier("hello-world_mqttReplyOutbound") MessageHandler mqttReplyOutbound) {
        return super.mqttReplyOutboundFlow(mqttReplyOutbound);
    }

    @Bean("hello-world_mqttReplyOutbound")
    @Override
    public MessageHandler mqttReplyOutbound(
            MqttPahoClientFactory factory,
            JsonPahoMessageConverter converter) {
        return super.mqttReplyOutbound(factory, converter);
    }


}
