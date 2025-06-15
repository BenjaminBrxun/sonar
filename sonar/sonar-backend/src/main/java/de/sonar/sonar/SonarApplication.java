package de.sonar.sonar;

import de.sonar.sonar.controllers.EventController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class SonarApplication {

    public static void main(String[] args) {
        SpringApplication.run(SonarApplication.class, args);
    }

}
