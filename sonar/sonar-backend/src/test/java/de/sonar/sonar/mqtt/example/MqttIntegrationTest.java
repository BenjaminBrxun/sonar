package de.sonar.sonar.mqtt.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessageChannel;

import java.util.Arrays;

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

    @Autowired
    private ApplicationContext applicationContext;


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

    @Test
    void printAllMessageChannelBeans() {
        String[] beanNames = applicationContext.getBeanNamesForType(MessageChannel.class);

        System.out.println("\n=== Message Channel Beans ===");
        Arrays.stream(beanNames)
                .sorted()
                .forEach(beanName -> {
                    MessageChannel channel = applicationContext.getBean(beanName, MessageChannel.class);
                    System.out.printf("Bean Name: %-50s | Type: %s%n",
                            beanName,
                            channel.getClass().getSimpleName());
                });
    }



}
