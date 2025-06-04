package de.sonar.sonar.mqtt.example;

import de.sonar.sonar.mqtt.base.config.AbstractRequesterMqttConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldTopicMqttRequesterConfig extends AbstractRequesterMqttConfig {
    @Override
    protected String getTopic() {
        return "hello-world";
    }
}

