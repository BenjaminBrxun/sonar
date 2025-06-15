package de.sonar.sonar.mqtt.base.request;

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

    private UUID requestId;
}
