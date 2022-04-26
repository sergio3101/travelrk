package ru.flystar.travelrk.domain.persistents;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 30.10.2017.
 */
@Entity
@Table(name = "exclusiveTour")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExclusiveTour extends BaseId {
  @Column(name = "path", nullable = true, length = 1024)
  private String path;

  @Column(name = "name", nullable = true, length = 1024)
  private String name;

  @Column(name = "description", nullable = true, length = 1024)
  private String description = "";

  @Column(name = "size", nullable = true, length = 45)
  private String size = "";

  @Column(name = "logo", nullable = true, length = 254)
  private String logo = "";

  @Column(name = "latitude", nullable = true, length = 20)
  private String latitude = "";

  @Column(name = "longitude", nullable = true, length = 20)
  private String longitude = "";

  @Column(name = "height", nullable = true, length = 20)
  private String height = "0";

  @Column(name = "north", nullable = true, length = 20)
  private String north = "0";

  @DateTimeFormat(pattern = "yyyy/MM/dd")
  @Column(name = "dateOfDownload", nullable = true)
  private Date dateOfDownload;

  @Column(name = "aRenta", nullable = true)
  private boolean aRenta = false;

  @DateTimeFormat(pattern = "yyyy/MM/dd")
  @Column(name = "rentaExpired", nullable = true)
  private Date rentaExpired;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "exclusivetour_panoramas",
      joinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "pano_id", referencedColumnName = "id"))
  private Set<Panorama> hsForRenta = new HashSet<>();

  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "exclusiveTour", cascade = CascadeType.ALL)
  @OrderBy("name ASC")
  private List<Scene> scenes = new ArrayList<>();

  @Column(name = "krpanoXml", nullable = false)
  @Type(type = "text")
  private String krpanoXml = "";

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ExclusiveTour that = (ExclusiveTour) o;
    if (getId() != that.getId()) return false;
    if (path != null ? !path.equals(that.path) : that.path != null) return false;
    return true;
  }

  @Override
  public int hashCode() {
    int result = getId();
    result = 31 * result + (path != null ? path.hashCode() : 0);
    return result;
  }
}
