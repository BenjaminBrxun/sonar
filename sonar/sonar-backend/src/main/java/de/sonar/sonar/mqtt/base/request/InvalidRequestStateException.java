package de.sonar.sonar.mqtt.base.request;

public class InvalidRequestStateException extends RuntimeException {

    public InvalidRequestStateException(String message) {
        super(message);
    }

}
