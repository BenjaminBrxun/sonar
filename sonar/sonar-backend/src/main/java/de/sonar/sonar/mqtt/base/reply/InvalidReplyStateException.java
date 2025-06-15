package de.sonar.sonar.mqtt.base.reply;

/**
 * Exception thrown when an MQTT request is in an invalid state.
 */
public class InvalidReplyStateException extends RuntimeException {

    public InvalidReplyStateException(String message) {
        super(message);
    }

}
