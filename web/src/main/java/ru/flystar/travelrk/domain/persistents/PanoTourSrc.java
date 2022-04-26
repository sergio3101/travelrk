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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "panotoursrc")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {
    "scenes",
    "srcXml",
    "msgXml"
})
public class PanoTourSrc extends BaseId {
  @Column(name = "path", nullable = true, length = 1024)
  private String path;

  @Column(name = "name", nullable = true, length = 1024)
  private String name;

  @Column(name = "description", nullable = true, length = 1024)
  private String description = "";

  @DateTimeFormat(pattern = "yyyy/MM/dd")
  @Column(name = "dateOfCreate", nullable = true)
  private Date dateOfCreate;

  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "panoTourSrc", cascade = CascadeType.ALL)
  private List<PanoTourScene> scenes = new ArrayList<>();

  @Column(name = "srcXml", nullable = false)
  @Type(type = "text")
  private String srcXml = "";

  @Column(name = "msgXml", nullable = false)
  @Type(type = "text")
  private String msgXml = "";

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PanoTourSrc that = (PanoTourSrc) o;
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
