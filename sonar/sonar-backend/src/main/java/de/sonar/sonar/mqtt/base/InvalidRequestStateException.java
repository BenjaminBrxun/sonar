package de.sonar.sonar.mqtt.base;

public class InvalidRequestStateException extends RuntimeException {
    public InvalidRequestStateException(String message) {
        super(message);
    }
}
