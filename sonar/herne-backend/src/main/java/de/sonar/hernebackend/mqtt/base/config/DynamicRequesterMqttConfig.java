package de.sonar.hernebackend.mqtt.base.config;

import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
public class DynamicRequesterMqttConfig extends AbstractRequesterMqttConfig {

    protected String topic;

    @Override
    protected String getTopic() {
        return this.topic;
    }

}
