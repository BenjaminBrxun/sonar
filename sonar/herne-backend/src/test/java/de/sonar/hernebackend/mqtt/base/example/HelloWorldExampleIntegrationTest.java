package de.sonar.hernebackend.mqtt.base.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class HelloWorldExampleIntegrationTest {

    @Autowired
    HelloWorldRequestService helloWorldRequestService;

    // This integration test is only for manual testing
    // To run this test, start the mqtt-broker and the herne-backend.
    // Then execute the test.
    @Disabled
    @Test
    public void testHelloWorld(CapturedOutput output) {
        // Act
        helloWorldRequestService.helloWorld();

        // Assert
        assertTrue(output.toString().contains("Sending request: Hello World!"));
        assertTrue(output.toString().contains("Received response: Hello World You Too!"));
    }

}
