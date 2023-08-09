package adeo.leroymerlin.cdp.api.dto;

import java.util.Set;

public class BandDto {

    private Long id;

    private String name;

    public BandDto() {
    }

    public BandDto(Long id, String name, Set<MemberDto> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }

    public Long getId() {
        return id;
    }


    private Set<MemberDto> members;

    public Set<MemberDto> getMembers() {
        return members;
    }

    public void setMembers(Set<MemberDto> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
