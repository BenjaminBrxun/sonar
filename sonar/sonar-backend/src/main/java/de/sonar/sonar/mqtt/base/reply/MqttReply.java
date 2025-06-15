package de.sonar.sonar.mqtt.base.reply;

import de.sonar.sonar.mqtt.base.request.MqttRequest;
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

    /**
     * The ID of the related {@link MqttRequest}
     */
    private UUID requestId;
}
