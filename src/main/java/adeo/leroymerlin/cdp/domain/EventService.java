package adeo.leroymerlin.cdp.domain;

import adeo.leroymerlin.cdp.domain.exceptions.NotFoundException;
import adeo.leroymerlin.cdp.domain.model.Event;
import adeo.leroymerlin.cdp.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (!eventOptional.isPresent()) {
            throw new NotFoundException("Event with this id does not exist on the database");
        }
        eventRepository.delete(id);
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();

        return events.stream()
                .filter(event -> event.getBands().stream()
                        .anyMatch(band -> band.getMembers()
                                .stream()
                                .anyMatch(member -> member.getName().toLowerCase().contains(query.toLowerCase()))
                        ))
                .collect(Collectors.toList());
    }

    @Transactional
    public Event updateEvent(Long id, Event event) {

        if (event.getId() != null && !event.getId().equals(id)) {
            throw new IllegalArgumentException("Id doesn't match the event payload id");
        }
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (!eventOptional.isPresent()) {
            throw new NotFoundException("Event with this id does not exist on the database");
        }
        Event existingEvent = eventOptional.get();

        existingEvent.setTitle(event.getTitle());
        existingEvent.setBands(event.getBands());
        existingEvent.setImgUrl(event.getImgUrl());
        existingEvent.setNbStars(event.getNbStars());
        existingEvent.setComment(event.getComment());

        return existingEvent;
    }
}
