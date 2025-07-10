package de.sonar.sonar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.sonar.sonar.model.enums.EventStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    public Event(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.headline = builder.headline;
        this.address = builder.address;
        this.categories = builder.categories;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.applicant = builder.applicant;
        this.processor = builder.processor;
        this.deletedStateAt = builder.deletedStateAt;
        this.price = builder.price;
        this.minAge = builder.minAge;
        this.restricted = builder.restricted;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    private String headline;

    @Embedded
    private Address address;

    @ManyToMany
    private List<Category> categories;

    @Column(nullable = false)
    private OffsetDateTime startDate;

    @Column(nullable = false)
    private OffsetDateTime endDate;

    @ManyToOne
    private Organizer applicant;

    @ManyToOne
    private AdministrativeUser processor;

    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.UNDER_EDITING;

    @JsonIgnore
    private OffsetDateTime deletedStateAt;

    private float price;

    private int minAge;

    private boolean restricted;

    public boolean isNew() {
        return this.id <= 0;
    }

    // Referenzimplementierung für das Builder-Pattern
    public static class Builder {
        private String name;
        private String description;
        private String headline;
        private Address address;
        private List<Category> categories;
        private OffsetDateTime startDate;
        private OffsetDateTime endDate;
        private EventStatus status;
        private OffsetDateTime deletedStateAt;
        private Organizer applicant;
        private AdministrativeUser processor;
        private float price;
        private int minAge;
        private boolean restricted;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder headline(String headline) {
            this.headline = headline;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public Builder categories(List<Category> categories) {
            this.categories = categories;
            return this;
        }

        public Builder startDate(OffsetDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(OffsetDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder status(EventStatus status) {
            this.status = status;
            return this;
        }

        public Builder deletedStateAt(OffsetDateTime deletedStateAt) {
            this.deletedStateAt = deletedStateAt;
            return this;
        }

        public Builder applicant(Organizer applicant) {
            this.applicant = applicant;
            return this;
        }

        public Builder processor(AdministrativeUser processor) {
            this.processor = processor;
            return this;
        }

        public Builder price(float price) {
            this.price = price;
            return this;
        }

        public Builder minAge(int minAge) {
            this.minAge = minAge;
            return this;
        }

        public Builder restricted(boolean restricted) {
            this.restricted = restricted;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }

}
