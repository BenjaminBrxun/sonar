package de.sonar.sonar.mqtt.base.request;

import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
public class DynamicRequestMqttConfig extends AbstractRequestMqttConfig {

    protected String topic;

    @Override
    protected String getTopic() {
        return this.topic;
    }

}
