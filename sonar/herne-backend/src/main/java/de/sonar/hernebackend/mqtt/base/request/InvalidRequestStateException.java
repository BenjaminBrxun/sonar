package de.sonar.hernebackend.mqtt.base.request;

/**
 * Exception thrown when an {@link MqttRequest} is in an invalid state.
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

    /**
     * Constructs a new {@link InvalidRequestStateException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause   the cause of the exception
     */
    public InvalidRequestStateException(String message, Throwable cause) {
        super(message, cause);
    }

}
