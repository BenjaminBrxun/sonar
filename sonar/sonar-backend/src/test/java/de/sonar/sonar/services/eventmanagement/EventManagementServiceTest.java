package de.sonar.sonar.services.eventmanagement;

import de.sonar.sonar.model.Event;
import de.sonar.sonar.model.state.*;
import de.sonar.sonar.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@SpringBootTest
public class EventManagementServiceTest {

    @Autowired
    private EventManagementService eventManagementService;

    @Autowired
    private EventRepository eventRepository;

    private Event testEvent;

    @BeforeEach
    void setUp() {
        testEvent = new Event();
        testEvent.setStartDate(new Date());
        testEvent.setEndDate(new Date(System.currentTimeMillis() + 3600000)); // 1 hour later
        testEvent = eventRepository.save(testEvent);
    }

    @Test
    void testCompleteEventStateTransition() {
        assertThat(testEvent.getEventState()).isInstanceOf(NewEventState.class);

        eventManagementService.processEvent(testEvent);

        testEvent = eventRepository.findById(testEvent.getId()).orElseThrow();
        assertThat(testEvent.getEventState()).isInstanceOf(EventInReviewState.class);

        eventManagementService.processEvent(testEvent);

        testEvent = eventRepository.findById(testEvent.getId()).orElseThrow();
        assertThat(testEvent.getEventState()).isInstanceOf(ApprovedEventState.class);

        eventManagementService.processEvent(testEvent);

        testEvent = eventRepository.findById(testEvent.getId()).orElseThrow();
        assertThat(testEvent.getEventState()).isInstanceOf(ApprovedEventState.class);

        eventManagementService.processEvent(testEvent);

        testEvent = eventRepository.findById(testEvent.getId()).orElseThrow();
        assertThat(testEvent.getEventState()).isInstanceOf(DeployedEventState.class);

        eventManagementService.processEvent(testEvent);

        testEvent = eventRepository.findById(testEvent.getId()).orElseThrow();
        assertThat(testEvent.getEventState()).isInstanceOf(ArchivedEventState.class);

        eventManagementService.processEvent(testEvent);

        testEvent = eventRepository.findById(testEvent.getId()).orElseThrow();
        assertThat(testEvent.getEventState()).isInstanceOf(NewEventState.class);
    }

    @Test
    void testDeleteTransition() {
        assertThat(testEvent.getEventState()).isInstanceOf(NewEventState.class);

        eventManagementService.deleteEvent(testEvent);

        testEvent = eventRepository.findById(testEvent.getId()).orElseThrow();
        assertThat(testEvent.getEventState()).isInstanceOf(DeleteEventState.class);
    }
}
