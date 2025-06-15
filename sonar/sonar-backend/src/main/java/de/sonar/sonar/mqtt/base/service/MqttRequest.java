package de.sonar.sonar.mqtt.base.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MqttRequest<RequestType> {

    private final String messageType = "REQUEST";

    private RequestType payload;

    private String requestTopic;

    private String responseTopic;

    private UUID requestId;
}
