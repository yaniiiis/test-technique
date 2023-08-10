package adeo.leroymerlin.cdp.api;

import adeo.leroymerlin.cdp.domain.EventService;
import adeo.leroymerlin.cdp.domain.exceptions.NotFoundException;
import adeo.leroymerlin.cdp.domain.model.Band;
import adeo.leroymerlin.cdp.domain.model.Event;
import adeo.leroymerlin.cdp.domain.model.Member;
import adeo.leroymerlin.cdp.repository.EventRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@ComponentScan("adeo.leroymerlin.cdp.domain")
public class EventServiceTests {
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    public void getEventsShouldReturnListOfEvents() {
        List<Event> mockedEvents = new ArrayList<>();
        mockedEvents.add(new Event(1L, "title1", "src://...", new HashSet<Band>(),
                5, "awesome event"));
        mockedEvents.add(new Event(2L, "title2", "src://...", new HashSet<Band>(),
                4, "awesome event"));
        Mockito.when(eventRepository.findAllBy()).thenReturn(mockedEvents);

        assertEquals(eventService.getEvents().size(), 2);
        Assertions.assertThat(eventService.getEvents()).hasSameElementsAs(mockedEvents);
    }

    @Test
    public void getFilteredEventsShouldReturnFiltredList() {
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

        Event matchingEvent = new Event(1L, "title1", "src://...", mockedBands1,
                5, "awesome event");
        Event notMatchingEvent = new Event(2L, "title2", "src://...", mockedBands2,
                4, "awesome event");

        allMockedEvents.add(matchingEvent);
        allMockedEvents.add(notMatchingEvent);

        Mockito.when(eventRepository.findAllBy()).thenReturn(allMockedEvents);

        expectedEvents.add(matchingEvent);

        Assertions.assertThat(eventService.getFilteredEvents("wa")).hasSameElementsAs(expectedEvents);
    }

    @Test
    public void getFilteredEventsShouldReturnEmptyListWhenNoMatchingMember() {
        List<Event> allMockedEvents = new ArrayList<>();

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

        Event noMatchingEvent1 = new Event(1L, "title1", "src://...", mockedBands1,
                5, "awesome event");
        Event notMatchingEvent2 = new Event(2L, "title2", "src://...", mockedBands2,
                4, "awesome event");

        allMockedEvents.add(noMatchingEvent1);
        allMockedEvents.add(notMatchingEvent2);

        Mockito.when(eventRepository.findAllBy()).thenReturn(allMockedEvents);

        Assertions.assertThat(eventService.getFilteredEvents("qo")).isEmpty();
    }

    @Test
    public void updateEventShouldReturnUpdatedEvent() {
        Event event = new Event(1L, "title1", "src://...", new HashSet<Band>(),
                5, "awesome event");
        Mockito.when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));
        assertEquals(eventService.updateEvent(event.getId(), event), event);
    }

    @Test(expected = NotFoundException.class)
    public void updateEventShouldThorowNotFoundExceptionWhenEventNotExist() {

        Event event = new Event(1L, "title1", "src://...", new HashSet<Band>(),
                5, "awesome event");
        Mockito.when(eventRepository.findById(event.getId())).thenReturn(Optional.empty());
        eventService.updateEvent(event.getId(), event);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateEventShouldThorowIllegalArgumentExceptionWhenBadParameters() {
        Event event = new Event(1L, "title1", "src://...", new HashSet<Band>(),
                5, "awesome event");
        eventService.updateEvent(3L, event);
    }
}
