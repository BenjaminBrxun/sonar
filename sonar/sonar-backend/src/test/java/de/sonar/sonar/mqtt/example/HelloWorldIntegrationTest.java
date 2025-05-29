package de.sonar.sonar.mqtt.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HelloWorldIntegrationTest {

    @Autowired
    HelloWorldMqttRequesterService helloWorldMqttRequesterService;

    // This integration test is only for manual testing
    @Disabled
    @Test
    public void testHelloWorld() {
        helloWorldMqttRequesterService.helloWorld();
    }

}
