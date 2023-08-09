package adeo.leroymerlin.cdp.api;

import adeo.leroymerlin.cdp.api.dto.EventDto;
import adeo.leroymerlin.cdp.api.mapping.EventMapper;
import adeo.leroymerlin.cdp.domain.model.Event;
import adeo.leroymerlin.cdp.domain.EventService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @Autowired
    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<EventDto> findEvents() {
       return eventMapper.mapEventToDtos(eventService.getEvents());
    }

    @RequestMapping(value = "/search/{query}", method = RequestMethod.GET)
    public List<Event> findEvents(@PathVariable String query) {
        return eventService.getFilteredEvents(query);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable Long id) {
        eventService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public EventDto updateEvent(@PathVariable Long id, @RequestBody EventDto event) throws NotFoundException {
       return eventMapper.mapEventToDto(eventService.updateEvent(id, eventMapper.mapEventDtoToEntity(event)));
    }
}
