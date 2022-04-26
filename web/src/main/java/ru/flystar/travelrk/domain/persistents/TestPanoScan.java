package ru.flystar.travelrk.domain.persistents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "testPanoScan")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TestPanoScan extends BaseId {
  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "testPanoScan", fetch = FetchType.EAGER)
  private Set<TestPano> panoramas = new HashSet<>();
}
