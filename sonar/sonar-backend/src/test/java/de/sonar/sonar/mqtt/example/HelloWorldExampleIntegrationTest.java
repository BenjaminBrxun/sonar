package de.sonar.sonar.mqtt.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HelloWorldExampleIntegrationTest {

    @Autowired
    HelloWorldRequestService helloWorldRequestService ;

    // This integration test is only for manual testing
    // To run this test, start the mqtt-broker and the herne-backend.
    // Then execute the test.
    @Disabled
    @Test
    public void testHelloWorld() {
        helloWorldRequestService.helloWorld();
    }

}
