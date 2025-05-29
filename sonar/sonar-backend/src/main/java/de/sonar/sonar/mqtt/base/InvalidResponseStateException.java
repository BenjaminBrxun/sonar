package de.sonar.sonar.mqtt.base;

public class InvalidResponseStateException extends RuntimeException {
    public InvalidResponseStateException(String message) {
        super(message);
    }
}
