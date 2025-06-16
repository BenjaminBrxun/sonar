package de.sonar.hernebackend.mqtt.base.reply;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Annotation to automatically register a {@link DynamicReplyMqttConfig}.
 * <p>
 * This annotation must be applied to classes that extend {@link AbstractMqttReplyService}
 * to automatically configure the MQTT reply handling infrastructure.
 * <p>
 * The automatic registration is handled by {@link MqttReplyServiceRegistrar}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MqttReplyServiceRegistrar.class)
public @interface RegisterReplyMqttConfig {

    /**
     * The MQTT topic to which this responder service will subscribe.
     * <p>
     * The default topic will be set to "reply/topic/" + this.topic()
     *
     * @return the topic name for this responder service
     */
    String topic();

}
