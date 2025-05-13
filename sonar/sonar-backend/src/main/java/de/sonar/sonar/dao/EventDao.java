package de.sonar.sonar.dao;

import java.util.List;

public class EventDao {

    int dings;

//    public void create(Object event);
//    public Object load(int id);
//    List<Object> find();

    @Override
    public String toString() {
        return "EventDao [dings=" + dings + "]";
    }

    public EventDto toDto() {
        return EventDto.builder()
                .costs(234.3f)
                .tag("")
                .name("")
            .build();
    }
}