package de.sonar.hernebackend.mqtt.base.service;

public class InvalidRequestStateException extends RuntimeException {

    public InvalidRequestStateException(String message) {
        super(message);
    }

}
