package de.sonar.sonar.mqtt.base;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * Mqtt config
 * <p>
 * With this config it is possible to implement a request-reply model (without queues)
 * <p>
 * For an implementation example go to:
 * - MqttPublisher
 * - MqttSubscriber
 */
@Configuration
@Slf4j
public class MqttConfig {

    @Value("${mqtt.server.uri}")
    private String mqttServerUri;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        log.info("Connect to MQTT Broker with URI: {}.", mqttServerUri);
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{mqttServerUri});
        options.setAutomaticReconnect(true);
        options.setKeepAliveInterval(30);
        options.setUserName("guest");
        options.setPassword("guest".toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public JsonPahoMessageConverter jsonPahoMessageConverter() {
        return new JsonPahoMessageConverter();
    }

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
            JsonPahoMessageConverter converter) {
        var handler = new MqttPahoMessageHandler("requestPublisherClient", factory);
        handler.setDefaultTopic("request/topic");
        handler.setAsync(true);
        handler.setDefaultQos(1);
        handler.setConverter(converter);
        return handler;
    }

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
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                "responseReceiverClient", factory, "request/topic");
        adapter.setConverter(converter);
        adapter.setQos(1);
        return adapter;
    }

    // 3. Outbound: Response

    @Bean
    public MessageChannel mqttReplyOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow mqttReplyOutboundFlow(MessageHandler mqttReplyOutbound) {
        return IntegrationFlow.from("mqttReplyOutboundChannel")
                .handle(mqttReplyOutbound)
                .get();
    }

    @Bean
    public MessageHandler mqttReplyOutbound(
            MqttPahoClientFactory factory,
            JsonPahoMessageConverter converter) {
        var handler = new MqttPahoMessageHandler(
                "responsePublisherClient", factory);
        handler.setDefaultTopic("reply/topic");
        handler.setAsync(true);
        handler.setDefaultQos(1);
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
        var adapter = new MqttPahoMessageDrivenChannelAdapter(
                "requestReceiverClient", factory, "reply/topic");
        adapter.setConverter(converter);
        adapter.setQos(1);
        adapter.setOutputChannel(mqttReplyInboundChannel());
        return adapter;
    }

}
