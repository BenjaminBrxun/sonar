package de.sonar.hernebackend.mqtt.base.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MqttResponse<ResponseType> {

    private final String messageType = "RESPONSE";

    private ResponseType payload;

    private Class<ResponseType> payloadType;

    private String responseTopic;

    private UUID requestId;
}
