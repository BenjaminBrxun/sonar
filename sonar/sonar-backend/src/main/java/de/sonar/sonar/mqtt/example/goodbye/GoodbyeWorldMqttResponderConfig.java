package de.sonar.sonar.mqtt.example.goodbye;

import de.sonar.sonar.mqtt.base.config.AbstractResponderMqttConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoodbyeWorldMqttResponderConfig extends AbstractResponderMqttConfig {

    @Override
    protected String getTopic() {
        return "goodbye-world";
    }

}
