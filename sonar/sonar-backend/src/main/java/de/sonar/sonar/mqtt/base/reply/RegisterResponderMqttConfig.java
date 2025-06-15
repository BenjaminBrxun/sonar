package de.sonar.sonar.mqtt.base.reply;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MqttResponderServiceRegistrar.class)
public @interface RegisterResponderMqttConfig {

    String topic();

}
