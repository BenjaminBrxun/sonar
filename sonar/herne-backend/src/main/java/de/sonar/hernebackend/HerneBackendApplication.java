package de.sonar.backendherne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HerneBackendApplication {

	public static void main(String[] args) {
		System.out.println("Herne Backend");
		SpringApplication.run(HerneBackendApplication.class, args);
	}

}
