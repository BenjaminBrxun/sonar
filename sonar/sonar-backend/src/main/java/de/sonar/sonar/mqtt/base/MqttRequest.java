package de.sonar.sonar.mqtt.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MqttRequest<RequestType> {

    private RequestType payload;

    private String requestTopic;

    private String responseTopic;

    private UUID requestId;
}
