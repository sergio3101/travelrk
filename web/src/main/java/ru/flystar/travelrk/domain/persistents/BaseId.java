package ru.flystar.travelrk.domain.persistents;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 31.10.2017.
 */

@MappedSuperclass
@Getter
@Setter
public class BaseId {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private int id;

  public BaseId() {
  }

  public BaseId(int id) {
    this.id = id;
  }
}