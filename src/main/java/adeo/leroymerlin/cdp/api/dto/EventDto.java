package adeo.leroymerlin.cdp.api.dto;

import java.util.Set;

public class EventDto {

    private Long id;

    private String title;

    private String imgUrl;

    private Set<BandDto> bands;

    private Integer nbStars;

    private String comment;

    public EventDto() {
    }

    public EventDto(Long id, String title, String imgUrl, Set<BandDto> bands, Integer nbStars, String comment) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
        this.bands = bands;
        this.nbStars = nbStars;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Set<BandDto> getBands() {
        return bands;
    }

    public void setBands(Set<BandDto> bands) {
        this.bands = bands;
    }

    public Integer getNbStars() {
        return nbStars;
    }

    public void setNbStars(Integer nbStars) {
        this.nbStars = nbStars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
