package ru.flystar.travelrk.domain.persistents;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 15.12.2017.
 */
@Entity
@Table(name = "scene")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Scene extends BaseId {
    private String name;
    private String dir;
    private String title;
    private String latitude;
    private String longitude;
    private String north;
    private String height;

    @ManyToOne
    @JoinColumn(name = "exclusiveTour_id")
    private ExclusiveTour exclusiveTour;

    public boolean haveGeoMetaData() {
        return (latitude!=null&&longitude!=null&&height!=null&&north!=null) &&
                (!longitude.isEmpty()&&!latitude.isEmpty()&&!height.isEmpty()&&!north.isEmpty());
    }
}
