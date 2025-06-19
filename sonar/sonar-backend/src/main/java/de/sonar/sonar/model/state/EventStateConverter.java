package de.sonar.sonar.model.state;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EventStateConverter implements AttributeConverter<EventState, String> {

    @Override
    public String convertToDatabaseColumn(EventState eventState) {
        if (eventState == null) {
            return null;
        }
        return eventState.getClass().getSimpleName();

    }

    @Override
    public EventState convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        try {
            Class<?> stateClass = Class.forName("de.sonar.sonar.model.state." + s);
            return (EventState) stateClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Error converting state", e);
        }

    }

}
