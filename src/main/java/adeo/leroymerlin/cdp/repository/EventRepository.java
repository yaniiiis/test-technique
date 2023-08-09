package adeo.leroymerlin.cdp.repository;

import adeo.leroymerlin.cdp.domain.model.Event;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends Repository<Event, Long> {

    void delete(Long eventId);

    List<Event> findAllBy();

    Optional<Event> findById(Long id);

    Event save(Event event);
}
