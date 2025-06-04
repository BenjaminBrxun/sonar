package de.sonar.hernebackend.mqtt.base.service;

public class InvalidResponseStateException extends RuntimeException {

    public InvalidResponseStateException(String message) {
        super(message);
    }

}
