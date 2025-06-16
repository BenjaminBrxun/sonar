package de.sonar.sonar.mqtt.base.request;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
@ConditionalOnBean(value = AbstractMqttRequestService.class)
public class DynamicRequestMqttConfig extends AbstractRequestMqttConfig {

}
