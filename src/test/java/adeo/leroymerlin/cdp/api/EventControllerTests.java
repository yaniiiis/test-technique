package adeo.leroymerlin.cdp.api;


import adeo.leroymerlin.cdp.api.dto.BandDto;
import adeo.leroymerlin.cdp.api.dto.EventDto;
import adeo.leroymerlin.cdp.domain.EventService;
import adeo.leroymerlin.cdp.domain.model.Band;
import adeo.leroymerlin.cdp.domain.model.Event;
import adeo.leroymerlin.cdp.domain.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
@ComponentScan("adeo.leroymerlin.cdp.api")
public class EventControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getEventsShouldReturnEvents() throws Exception {

        List<Event> mockedEvents = new ArrayList<>();
        mockedEvents.add(new Event(1L, "title1", "src://...", new HashSet<Band>(),
                5, "awesome event"));
        mockedEvents.add(new Event(2L, "title2", "src://...", new HashSet<Band>(),
                4, "awesome event"));
        Mockito.when(eventService.getEvents()).thenReturn(mockedEvents);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/events/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0]title").value("title1"))
                .andExpect(jsonPath("$.[1]title").value("title2"));


    }

    @Test
    public void updateEventShouldReturnUpdatedEvent() throws Exception {
        EventDto eventDto = new EventDto(1L, "title1", "src://...", new HashSet<BandDto>(),
                5, "awesome event");

        Event event = new Event(1L, "title1", "src://...", new HashSet<Band>(),
                5, "awesome event");

        Mockito.when(eventService.updateEvent(Mockito.eq(event.getId()), Mockito.anyObject())).thenReturn(event);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/events/" + event.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(eventDto));
    }

    @Test
    public void deleteEventShouldReturnStatusIsOk() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/events/1002"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void filterEventShouldReturnFiltredEvent() throws Exception {
        List<Event> mockedEvents = new ArrayList<>();
        Set<Band> mockedBands1 = new HashSet<>();
        Set<Band> mockedBands2 = new HashSet<>();
        Set<Member> members1 = new HashSet<>();
        Set<Member> members2 = new HashSet<>();

        members1.add(new Member(1L, "warning"));
        members1.add(new Member(2L, "silence"));
        members2.add(new Member(3L, "water"));
        members2.add(new Member(4L, "wastern"));

        mockedBands1.add(new Band(1L, "band1", members1));
        mockedBands2.add(new Band(2L, "band2", members2));

        mockedEvents.add(new Event(1L, "title1", "src://...", mockedBands1,
                5, "awesome event"));
        mockedEvents.add(new Event(2L, "title2", "src://...", mockedBands2,
                4, "awesome event"));

        Mockito.when(eventService.getFilteredEvents("wa")).thenReturn(mockedEvents);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/events/search/wa"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
