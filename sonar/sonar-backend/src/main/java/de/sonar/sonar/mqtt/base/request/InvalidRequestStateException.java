package de.sonar.sonar.mqtt.base.request;

/**
 * Exception thrown when an MQTT request is in an invalid state.
 */
public class InvalidRequestStateException extends RuntimeException {

    /**
     * Constructs a new {@link InvalidRequestStateException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidRequestStateException(String message) {
        super(message);
    }

}
