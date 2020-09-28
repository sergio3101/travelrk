package ru.flystar.travelrk.domain.persistents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 30.10.2017.
 */
@Entity
@Table(name = "region")
@Setter
@Getter
@NoArgsConstructor
public class Region extends BaseId {
  @Column(name = "name", nullable = true, length = 45)
  private String name;

  @Column(name = "viewName", nullable = true, length = 45)
  private String viewName;

  @Column(name = "description", nullable = true, length = -1)
  private String description;

  @Column(name = "region_id", nullable = false)
  private Integer region_id;

//    @OneToOne
//    @JoinColumn(name = "region_id", referencedColumnName = "id", nullable = false)
//    private Region parent;

  public Region(int id) {
    super(id);
  }

  public Region(String name) {
    this.name = name;
  }

  public Region(String name, String viewName) {
    this.name = name;
    this.viewName = viewName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Region region = (Region) o;
    return name.equals(region.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
