package adeo.leroymerlin.cdp.domain.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(fetch=FetchType.EAGER)
    private Set<Member> members;

    public Band(Long id, String name, Set<Member> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }

    public Band() {
    }

    public Long getId() {
        return id;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
