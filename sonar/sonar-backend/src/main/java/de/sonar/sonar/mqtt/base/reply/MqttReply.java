package de.sonar.sonar.mqtt.base.reply;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MqttReply<ReplyType> {

    private final String messageType = "REPLY";

    private ReplyType payload;

    private String responseTopic;

    private UUID requestId;
}
