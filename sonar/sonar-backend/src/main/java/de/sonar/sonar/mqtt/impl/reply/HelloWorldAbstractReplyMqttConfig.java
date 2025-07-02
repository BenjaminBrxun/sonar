package de.sonar.sonar.mqtt.impl.reply;

import de.sonar.sonar.mqtt.base.reply.AbstractReplyMqttConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldAbstractReplyMqttConfig extends AbstractReplyMqttConfig {

    @Override
    protected String getTopic() {
        return "hello-world";
    }

}
