package adeo.leroymerlin.cdp.domain;

import adeo.leroymerlin.cdp.domain.model.Band;
import adeo.leroymerlin.cdp.domain.model.Event;
import adeo.leroymerlin.cdp.domain.model.Member;
import adeo.leroymerlin.cdp.repository.EventRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        eventRepository.delete(id);
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();

        List<Event> filtredEvents = events.stream()
                .filter(event -> event.getBands().stream()
                .anyMatch(band -> band.getMembers()
                        .stream()
                        .anyMatch(member ->
                                member.getName().toLowerCase().contains(query.toLowerCase()))))
                .collect(Collectors.toList());

        return filtredEvents;
    }

    @Transactional
    public Event updateEvent(Long id, Event event) throws NotFoundException {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if(!eventOptional.isPresent())
            throw new NotFoundException("Event with this id does not exist on the database");
        Event existingEvent = eventOptional.get();
        existingEvent.setTitle(event.getTitle());
        existingEvent.setBands(event.getBands());
        existingEvent.setImgUrl(event.getImgUrl());
        existingEvent.setNbStars(event.getNbStars());
        existingEvent.setComment(event.getComment());

        return existingEvent;
    }
}
