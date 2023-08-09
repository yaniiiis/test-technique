package adeo.leroymerlin.cdp.api;

import adeo.leroymerlin.cdp.domain.EventService;
import adeo.leroymerlin.cdp.domain.model.Band;
import adeo.leroymerlin.cdp.domain.model.Event;
import adeo.leroymerlin.cdp.domain.model.Member;
import adeo.leroymerlin.cdp.repository.EventRepository;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@ComponentScan("adeo.leroymerlin.cdp.domain")
public class EventServiceTests {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    public void shoudReturnListOfEvent(){
        List<Event> mockedEvents = new ArrayList<>();
        mockedEvents.add(new Event(1L, "title1", "src://...", new HashSet<Band>(),
                5, "awesome event"));
        mockedEvents.add(new Event(2L, "title2", "src://...", new HashSet<Band>(),
                4, "awesome event"));
        Mockito.when(eventRepository.findAllBy()).thenReturn(mockedEvents);

        assertEquals(eventService.getEvents().size(),2);

    }

    @Test
    public void shouldReturnFiltredList(){
        List<Event> allMockedEvents = new ArrayList<>();
        List<Event> expectedEvents = new ArrayList<>();

        Set<Band> mockedBands1 = new HashSet<>();
        Set<Band> mockedBands2 = new HashSet<>();
        Set<Member> members1 = new HashSet<>();
        Set<Member> members2 = new HashSet<>();

        members1.add(new Member(1L, "walter"));
        members1.add(new Member(2L, "silence"));
        members2.add(new Member(3L, "Yanis"));
        members2.add(new Member(4L, "Lamine"));

        mockedBands1.add(new Band(1L, "band1", members1));
        mockedBands2.add(new Band(2L, "band2", members2));

        allMockedEvents.add(new Event(1L, "title1", "src://...", mockedBands1,
                5, "awesome event"));
        allMockedEvents.add(new Event(2L, "title2", "src://...", mockedBands2,
                4, "awesome event"));

        Mockito.when(eventRepository.findAllBy()).thenReturn(allMockedEvents);

        expectedEvents.add(new Event(1L, "title1", "src://...", mockedBands1,
                5, "awesome event"));

        assertEquals(eventService.getFilteredEvents("wa"),expectedEvents);
    }

    @Test
    public void shouldReturnUpdatedEvent() throws NotFoundException {
        Event event = new Event(1L, "title1", "src://...", new HashSet<Band>(),
                5, "awesome event");

        Mockito.when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));
        assertEquals(eventService.updateEvent(event.getId(),event),event);
    }
}
