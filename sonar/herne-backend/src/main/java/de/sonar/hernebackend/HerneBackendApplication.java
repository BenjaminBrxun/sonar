package de.sonar.backendherne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendHerneApplication {

	public static void main(String[] args) {
		System.out.println("Herne Backend");
		SpringApplication.run(BackendHerneApplication.class, args);
	}

}
