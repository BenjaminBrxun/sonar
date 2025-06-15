package de.sonar.sonar.mqtt.base.reply;

import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
public class DynamicReplyMqttConfig extends AbstractReplyMqttConfig {

    protected String topic;

    @Override
    protected String getTopic() {
        return this.topic;
    }

}

