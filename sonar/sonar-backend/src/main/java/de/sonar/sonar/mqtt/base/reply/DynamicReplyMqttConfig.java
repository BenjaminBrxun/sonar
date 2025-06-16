package de.sonar.sonar.mqtt.base.reply;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

/**
 * Dynamic configuration for MQTT reply handling.
 * <p>
 * Extends {@link AbstractReplyMqttConfig} to provide dynamic topic configuration capabilities.
 * <p>
 * Variations of this configuration are registered by the {@link MqttReplyServiceRegistrar}
 * according to the used annotations of {@link RegisterReplyMqttConfig} on implementations of {@link AbstractMqttReplyService}.
 */
@Configuration
@ConditionalOnBean(value = AbstractMqttReplyService.class)
public class DynamicReplyMqttConfig extends AbstractReplyMqttConfig {

}

