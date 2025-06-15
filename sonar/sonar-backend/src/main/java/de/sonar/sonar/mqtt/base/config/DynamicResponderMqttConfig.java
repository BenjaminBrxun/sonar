package de.sonar.sonar.mqtt.base.config;

import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
public class DynamicResponderMqttConfig extends AbstractResponderMqttConfig {

    protected String topic;

    @Override
    protected String getTopic() {
        return this.topic;
    }

}

