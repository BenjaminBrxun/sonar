package de.sonar.sonar.dao;

import lombok.Builder;

@Builder
public class EventDto {
    String name;
    String tag;
    int id;
    float costs;
    public EventDto() {}

    public EventDto(EventDao dao) {}
}
