package ru.flystar.travelrk.domain.persistents;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "panotourscene")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PanoTourScene extends BaseId implements Serializable {
  private String name;
  private String dir;
  private String title;
  private String latitude;
  private String longitude;
  private String north;
  private String height;
  private Boolean showCustomerInfo;

  @ManyToOne()
  @JoinColumn(name = "panoTourSrc_id")
  private PanoTourSrc panoTourSrc;

  // @ManyToMany(mappedBy = "scenasFowShow")
  // private Set<PanoTourRenta> rentatours = new HashSet<>();

  public boolean haveGeoMetaData() {
    return (latitude != null && longitude != null && height != null && north != null) &&
        (!longitude.isEmpty() && !latitude.isEmpty() && !height.isEmpty() && !north.isEmpty());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PanoTourScene scene = (PanoTourScene) o;

    if (getId() != scene.getId()) return false;
    if (name != null ? !name.equals(scene.name) : scene.name != null) return false;
    return true;
  }

  @Override
  public int hashCode() {
    int result = getId();
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

}
