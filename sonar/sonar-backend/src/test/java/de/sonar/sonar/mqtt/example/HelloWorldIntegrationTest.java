package de.sonar.sonar.mqtt.example;

import de.sonar.sonar.mqtt.example.goodbye.GoodbyeWorldMqttRequesterService;
import de.sonar.sonar.mqtt.example.hello.HelloWorldMqttRequesterService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HelloWorldIntegrationTest {

    @Autowired
    HelloWorldMqttRequesterService helloWorldMqttRequesterService;

    @Autowired
    GoodbyeWorldMqttRequesterService goodbyeWorldMqttRequesterService;

    // This integration test is only for manual testing
    @Disabled
    @Test
    public void testHelloWorld() {
        helloWorldMqttRequesterService.helloWorld();
    }

    // This integration test is only for manual testing
    @Disabled
    @Test
    public void testGoodbyeWorld() {
        goodbyeWorldMqttRequesterService.goodbyeWorld();
    }


}
