package de.sonar.sonar.mqtt.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessageChannel;

import java.util.Arrays;

@SpringBootTest
public class HelloWorldExampleIntegrationTest {

    @Autowired
    HelloWorldRequesterService helloWorldMqttRequesterService;


    // This integration test is only for manual testing
    @Disabled
    @Test
    public void testHelloWorld() {
        helloWorldMqttRequesterService.helloWorld();
    }

}
