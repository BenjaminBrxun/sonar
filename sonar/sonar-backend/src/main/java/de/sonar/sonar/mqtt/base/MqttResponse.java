package de.sonar.sonar.mqtt.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MqttResponse<ResponseType> {

    private ResponseType payload;

    private String responseTopic;

    private UUID requestId;
}
