package de.sonar.sonar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "de.sonar.sonar")
@EnableScheduling
public class SonarApplication {

    public static void main(String[] args) {
        SpringApplication.run(SonarApplication.class, args);
    }

}
