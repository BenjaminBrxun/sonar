package de.sonar.sonar.mqtt.example.goodbye;

import de.sonar.sonar.mqtt.base.config.AbstractRequesterMqttConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoodbyeWorldMqttRequesterConfig extends AbstractRequesterMqttConfig {

    @Override
    protected String getTopic() {
        return "goodbye-world";
    }

}
