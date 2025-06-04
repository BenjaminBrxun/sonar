package de.sonar.sonar.mqtt.base.service;

public class InvalidRequestStateException extends RuntimeException {

    public InvalidRequestStateException(String message) {
        super(message);
    }

}
