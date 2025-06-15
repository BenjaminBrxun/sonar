package de.sonar.sonar.mqtt.base.reply;

/**
 * Exception thrown when an {@link MqttReply} is in an invalid state.
 */
public class InvalidReplyStateException extends RuntimeException {

    /**
     * Constructs a new {@link InvalidReplyStateException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidReplyStateException(String message) {
        super(message);
    }

}
