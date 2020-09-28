package ru.flystar.travelrk.domain.persistents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 30.10.2017.
 */
@Entity
@Table(name = "panorama")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(value = {
    "hlookAt",
    "vlookAt",
    "description",
    "sceneXml",
    "categoryOfContent",
    "tourList",
    "text",
    "info",
    "latitude",
    "longitude",
    "north",
    "height",
    "fov",
    "va",
    "vc",
    "panoScan",
    "panoramaScan"
})
public class Panorama extends BaseId {
  @Column(name = "panoPath")
  private String panoPath = "";
  @Column(name = "title")
  private String title = "";
  @Column(name = "description")
  private String description = "";
  @Column(name = "text")
  private String text = "";
  @Column(name = "info")
  private String info = "";
  @Column(name = "aCompassOn")
  private boolean aCompassOn;
  @Column(name = "aAir")
  private boolean aAir;
  @Column(name = "latitude")
  private String latitude = "";
  @Column(name = "longitude")
  private String longitude = "";
  @Column(name = "north")
  private String north = "";
  @Column(name = "va")
  private String va = "";
  @Column(name = "vc")
  private String vc = "";
  @Column(name = "height", length = 20)
  private String height = "0";
  @Column(name = "hLookAt")
  private String hLookAt = "";
  @Column(name = "vLookAt")
  private String vLookAt = "";
  @Column(name = "fov")
  private String fov = "60";
  @Column(name = "sceneXml", length = 1024)
  private String sceneXml = "";
  @Column(name = "panoScan", length = 20)
  private String panoScan = "";

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @Column(name = "dateOfCreate", nullable = true)
  private Date dateOfCreate = new Date();

  @OneToOne
  @JoinColumn(name = "categoryOfContent_id", referencedColumnName = "id", nullable = false)
  private CategoryOfContent categoryOfContent;

  @OneToOne
  @JoinColumn(name = "region_id", referencedColumnName = "id", nullable = false)
  private Region region;

  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToMany(mappedBy = "hsForRenta")
  private Set<ExclusiveTour> tourList = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "scan_id", nullable = false)
  PanoScan panoramaScan;

  public Panorama(String panoPath) {
    this.panoPath = panoPath;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Panorama panorama = (Panorama) o;

    if (getId() != panorama.getId()) return false;
    if (panoPath != null ? !panoPath.equals(panorama.panoPath) : panorama.panoPath != null) return false;
    return true;
  }

  @Override
  public int hashCode() {
    int result = getId();
    result = 31 * result + (panoPath != null ? panoPath.hashCode() : 0);
    return result;
  }

  public boolean haveGeoMetaData() {
    return (latitude != null && longitude != null && height != null && north != null) &&
        (!longitude.isEmpty() && !latitude.isEmpty() && !height.isEmpty() && !north.isEmpty());
  }
}
