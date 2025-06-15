package de.sonar.sonar.mqtt.base.request;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MqttRequestServiceRegistrar.class)
public @interface RegisterRequestMqttConfig {

    String topic();

}
