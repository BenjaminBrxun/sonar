package de.sonar.sonar.mqtt.impl.request;

import de.sonar.sonar.mqtt.base.request.AbstractRequestMqttConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldAbstractRequestMqttConfig extends AbstractRequestMqttConfig {

    @Override
    protected String getTopic() {
        return "hello-world";
    }

}
