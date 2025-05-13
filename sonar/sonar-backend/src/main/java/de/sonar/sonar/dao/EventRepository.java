package de.sonar.sonar.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface EventRepository extends JpaRepository<Long, EventDao> {
//    private JdbcTemplate jdbcTemplate;
//
    EventDao save(EventDao dao);
//    public EventRepository(final JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public void create(Event event) {
//
//    }
//
//    @Override
//    public Event load(int id) {
//        return null;
//    }
//
//    @Override
//    public List<Event> find() {
//        return jdbcTemplate.query(
//                "SELECT * FROM events",
//                new RowMapper<Event>()
//        );
//    }
    List<EventDao> findAllByDings(int dings);
}
