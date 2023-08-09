package adeo.leroymerlin.cdp.api.mapping;


import adeo.leroymerlin.cdp.api.dto.BandDto;
import adeo.leroymerlin.cdp.api.dto.EventDto;
import adeo.leroymerlin.cdp.api.dto.MemberDto;
import adeo.leroymerlin.cdp.domain.model.Band;
import adeo.leroymerlin.cdp.domain.model.Event;
import adeo.leroymerlin.cdp.domain.model.Member;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    public List<EventDto> mapEventToDtos(List<Event> events) {
       return events.stream().map(this::mapEventToDto).collect(Collectors.toList());
    }

    private BandDto mapBandToDto(Band band) {
        return  new BandDto(band.getId(),
                band.getName(),
                band.getMembers().stream().map(this::mapMemberToDto).collect(Collectors.toSet())
        );
    }

    private MemberDto mapMemberToDto(Member member) {
        return new MemberDto(member.getId(),member.getName());
    }

    public Event mapEventDtoToEntity(EventDto event) {
        return  new Event(event.getId(),
                event.getTitle(),
                event.getImgUrl(),
                event.getBands().stream().map(this::mapBandDtoToBandEntity).collect(Collectors.toSet()),
                event.getNbStars(), event.getComment());
    }

    private Band  mapBandDtoToBandEntity(BandDto bandDto) {
        return  new Band(bandDto.getId(),
                bandDto.getName(),
                bandDto.getMembers().stream().map(this::mapMemberDtoToMemberEntity).collect(Collectors.toSet())
        );
    }

    private Member mapMemberDtoToMemberEntity(MemberDto memberDto) {
        return  new Member(memberDto.getId(),memberDto.getName());
    }

    public EventDto mapEventToDto(Event event) {
      return new EventDto(event.getId(),
                event.getTitle(),
                event.getImgUrl(),
                event.getBands().stream().map(this::mapBandToDto).collect(Collectors.toSet()),
                event.getNbStars(), event.getComment());
    }
}
