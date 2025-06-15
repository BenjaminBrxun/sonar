package de.sonar.sonar.mqtt.base.reply;

public class InvalidResponseStateException extends RuntimeException {

    public InvalidResponseStateException(String message) {
        super(message);
    }

}
