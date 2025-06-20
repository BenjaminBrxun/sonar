package de.sonar.sonar.services.eventmanagement;

import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.model.enums.EventStatus;
import de.sonar.sonar.repositories.EventRepository;
import de.sonar.sonar.services.EventLifecycleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EventLifecycleServiceTest {

    @Autowired
    EventLifecycleService eventLifecycleService;

    @Autowired
    EventRepository eventRepository;

    private Event testEvent;

    @BeforeEach
    void setUp() {
        testEvent = Event.builder()
                .name("Test Event")
                .startDate(new Date())
                .endDate(new Date())
                .status(EventStatus.UNDER_EDITING)
                .build();
    }

    @Test
    void testProceedEventLifecycle() {
        // Act & Assert

        // 1. Submit a new event
        eventLifecycleService.submitNewEvent(testEvent);
        assertThat(testEvent.getId()).isGreaterThan(0);
        assertThat(testEvent.getStatus()).isEqualTo(EventStatus.UNDER_EDITING);

        // 2. Progress through states
        eventLifecycleService.proceedEventLivecycle(testEvent);
        assertThat(testEvent.getStatus())
                .as("Event should be in review after first progression")
                .isEqualTo(EventStatus.IN_REVIEW);

        eventLifecycleService.proceedEventLivecycle(testEvent);
        assertThat(testEvent.getStatus())
                .as("Event should be approved after second progression")
                .isEqualTo(EventStatus.APPROVED);

        eventLifecycleService.proceedEventLivecycle(testEvent);
        assertThat(testEvent.getStatus())
                .as("Event should be deployed after third progression")
                .isEqualTo(EventStatus.DEPLOYED);

        eventLifecycleService.proceedEventLivecycle(testEvent);
        assertThat(testEvent.getStatus())
                .as("Event should be archived after fourth progression")
                .isEqualTo(EventStatus.ARCHIVED);

        eventLifecycleService.proceedEventLivecycle(testEvent);
        assertThat(testEvent.getStatus())
                .as("Event should be under editing again after fifth progression")
                .isEqualTo(EventStatus.UNDER_EDITING);
    }

    @Test
    void testEventDeclinePath() {
        // Act
        eventLifecycleService.submitNewEvent(testEvent);
        eventLifecycleService.proceedEventLivecycle(testEvent);

        // Assert initial state
        assertThat(testEvent.getStatus())
                .as("Event should be in review")
                .isEqualTo(EventStatus.IN_REVIEW);

        // Act - decline the event
        eventLifecycleService.declineEvent(testEvent);

        // Assert declined state
        assertThat(testEvent.getStatus())
                .as("Event should be declined")
                .isEqualTo(EventStatus.DECLINED);

        // Act - proceed back to editing
        eventLifecycleService.proceedEventLivecycle(testEvent);

        // Assert final state
        assertThat(testEvent.getStatus())
                .as("Event should return to under editing")
                .isEqualTo(EventStatus.UNDER_EDITING);
    }

    @Test
    void testEventCancelPath() {
        // Act
        eventLifecycleService.submitNewEvent(testEvent);
        eventLifecycleService.proceedEventLivecycle(testEvent);
        eventLifecycleService.proceedEventLivecycle(testEvent);
        eventLifecycleService.proceedEventLivecycle(testEvent);

        // Assert initial state
        assertThat(testEvent.getStatus())
                .as("Event should be deployed")
                .isEqualTo(EventStatus.DEPLOYED);

        // Act - decline the event
        eventLifecycleService.cancelEvent(testEvent);

        // Assert declined state
        assertThat(testEvent.getStatus())
                .as("Event should be cancelled")
                .isEqualTo(EventStatus.CANCELLED);

        // Act - proceed to archived state
        eventLifecycleService.proceedEventLivecycle(testEvent);

        // Assert final state
        assertThat(testEvent.getStatus())
                .as("Event should return to archived")
                .isEqualTo(EventStatus.ARCHIVED);
    }


}
