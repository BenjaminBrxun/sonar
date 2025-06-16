package de.sonar.sonar.mqtt.base;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

/**
 * Base configuration class for MQTT connectivity in the application.
 * <p>
 * Provides beans for MQTT client factory and message converter configuration.
 * This class handles the core MQTT connection settings and client factory initialization.
 */
@Configuration
@Slf4j
public class BaseMqttConfig {

    /**
     * The URI of the MQTT server to connect to.
     */
    @Value("${mqtt.server.uri}")
    private String mqttServerUri;

    /**
     * Creates and configures an MQTT client factory with connection options.
     *
     * @return configured {@link MqttPahoClientFactory} instance with an automatic reconnection enabled
     * and server URI set from configuration
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{mqttServerUri});
        options.setAutomaticReconnect(true);
        factory.setConnectionOptions(options);
        return factory;
    }

    /**
     * Creates a JSON message converter for MQTT messages.
     * <p>
     * This converter handles the serialization and deserialization of MQTT messages
     * to and from JSON format.
     *
     * @return new instance of {@link JsonPahoMessageConverter}
     */
    @Bean
    public JsonPahoMessageConverter jsonPahoMessageConverter() {
        return new JsonPahoMessageConverter();
    }
}