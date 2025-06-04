package de.sonar.sonar.mqtt.base.service;

public class InvalidResponseStateException extends RuntimeException {

    public InvalidResponseStateException(String message) {
        super(message);
    }

}
