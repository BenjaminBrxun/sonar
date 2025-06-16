package de.sonar.hernebackend.mqtt.base.request;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Annotation to automatically register a {@link DynamicRequestMqttConfig}.
 * <p>
 * This annotation must be applied to classes that extend {@link AbstractMqttRequestService}
 * to automatically configure the MQTT reply handling infrastructure.
 * <p>
 * The automatic registration is handled by {@link MqttRequestServiceRegistrar}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MqttRequestServiceRegistrar.class)
public @interface RegisterRequestMqttConfig {

    /**
     * The MQTT topic to which this responder service will subscribe.
     * <p>
     * The default topic will be set to "request/topic/" + this.topic()
     *
     * @return the topic name for this responder service
     */
    String topic();

}
