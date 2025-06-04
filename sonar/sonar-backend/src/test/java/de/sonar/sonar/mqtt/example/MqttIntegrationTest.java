package de.sonar.sonar.mqtt.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MqttIntegrationTest {

    @Autowired
    HelloWorldMqttRequesterService helloWorldMqttRequesterService;

    @Autowired
    GoodbyeWorldMqttRequesterService goodbyeWorldMqttRequesterService;

    @Autowired
    EventsMqttRequesterService eventsMqttRequesterService;

    @Autowired
    EventMqttRequesterService eventMqttRequesterService;

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

    // This integration test is only for manual testing
    @Disabled
    @Test
    public void testChiquitaBananas() {
        eventsMqttRequesterService.turnChiquitasIntoBananas();
    }

    // This integration test is only for manual testing
    @Disabled
    @Test
    public void testChiquitaBanana() {
        eventMqttRequesterService.turnChiquitaIntoBanana();
    }


}
