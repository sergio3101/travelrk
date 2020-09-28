package ru.flystar.travelrk.domain.persistents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "testPano")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TestPano extends BaseId {
  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "scan_id", nullable = false)
  TestPanoScan testPanoScan;

}
