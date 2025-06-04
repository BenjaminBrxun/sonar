package de.sonar.sonar.mqtt.example.hello;

import de.sonar.sonar.mqtt.base.config.AbstractResponderMqttConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldTopicMqttResponderConfig extends AbstractResponderMqttConfig {

    @Override
    protected String getTopic() {
        return "hello-world";
    }

}
