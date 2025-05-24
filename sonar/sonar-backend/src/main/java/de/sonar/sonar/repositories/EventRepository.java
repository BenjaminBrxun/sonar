package de.sonar.sonar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    public Event findByName(String name);

    List<Event> findAllByAdress(Adress adress);


}
