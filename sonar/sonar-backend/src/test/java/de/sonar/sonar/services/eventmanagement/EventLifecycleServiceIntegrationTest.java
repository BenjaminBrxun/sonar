package de.sonar.sonar.services.eventmanagement;

import de.sonar.sonar.model.entity.Event;
import de.sonar.sonar.model.enums.EventStatus;
import de.sonar.sonar.repositories.EventRepository;
import de.sonar.sonar.services.EventLifecycleService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.OffsetDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EventLifecycleServiceIntegrationTest {

    @Autowired
    EventLifecycleService eventLifecycleService;

    @Autowired
    EventRepository eventRepository;

    private Event testEvent;

    @BeforeEach
    void setUp() {
        testEvent = Event.builder()
                .name("Test Event")
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now())
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

    @ParameterizedTest
    @MethodSource("provideEventStates")
    void testDeleteEventFromDifferentStates(EventStatus initialStatus) {
        // Arrange
        testEvent.setStatus(initialStatus);
        eventRepository.save(testEvent);

        // Act
        eventLifecycleService.deleteEvent(testEvent);

        // Assert deleted state
        assertThat(testEvent.getStatus())
                .as("Event should be deleted")
                .isEqualTo(EventStatus.DELETED);
    }

    private static Stream<Arguments> provideEventStates() {
        return Stream.of(
                Arguments.of(EventStatus.UNDER_EDITING),
                Arguments.of(EventStatus.IN_REVIEW),
                Arguments.of(EventStatus.APPROVED),
                Arguments.of(EventStatus.DECLINED),
                Arguments.of(EventStatus.DEPLOYED),
                Arguments.of(EventStatus.CANCELLED),
                Arguments.of(EventStatus.ARCHIVED)
        );
    }


}
