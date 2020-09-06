package ru.flystar.travelrk.domain.persistents;

import lombok.*;

import javax.persistence.*;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 30.10.2017.
 */
@Entity
@Table(name = "video")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Video extends BaseId {
    @Column(name = "youtubeID", nullable = true, length = 45)
    private String youtubeId;

    @Column(name = "title", nullable = true, length = 45)
    private String title;

    @Column(name = "description", nullable = true, length = -1)
    private String description;

    @Column(name = "latitude", nullable = true, length = 20)
    private String latitude;

    @Column(name = "longitude", nullable = true, length = 20)
    private String longitude;

    @OneToOne
    @JoinColumn(name = "categoryOfContent_id", referencedColumnName = "id", nullable = false)
    private CategoryOfContent categoryOfContent;

    @OneToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id", nullable = false)
    private Region region;

    public Video(String youtubeID, String title, Region region, CategoryOfContent categoryOfContent) {
        this.youtubeId = youtubeID;
        this.title = title;
        this.region = region;
        this.categoryOfContent = categoryOfContent;
    }

    public Video(String youtubeID, String title) {
        this.youtubeId = youtubeID;
        this.title = title;
    }

    public Video(String youtubeID) {
        this.youtubeId = youtubeID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        if (getId() != video.getId()) return false;
        if (youtubeId != null ? !youtubeId.equals(video.youtubeId) : video.youtubeId != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (youtubeId != null ? youtubeId.hashCode() : 0);
        return result;
    }
}
