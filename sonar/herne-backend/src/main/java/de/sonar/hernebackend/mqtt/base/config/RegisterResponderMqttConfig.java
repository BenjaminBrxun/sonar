package de.sonar.hernebackend.mqtt.base.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MqttResponderServiceRegistrar.class)
public @interface RegisterResponderMqttConfig {

    String topic();

}
