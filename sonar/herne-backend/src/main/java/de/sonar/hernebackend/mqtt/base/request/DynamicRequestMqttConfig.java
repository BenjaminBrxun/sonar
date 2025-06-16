package de.sonar.hernebackend.mqtt.base.request;

import org.springframework.context.annotation.Configuration;

/**
 * Dynamic configuration for MQTT request handling.
 * <p>
 * Extends {@link AbstractRequestMqttConfig} to provide dynamic topic configuration capabilities.
 * <p>
 * Variations of this configuration are registered by the {@link MqttRequestServiceRegistrar}
 * according to the used annotations of {@link RegisterRequestMqttConfig} on implementations of {@link AbstractMqttRequestService}.
 */
@Configuration
public class DynamicRequestMqttConfig extends AbstractRequestMqttConfig {

}
